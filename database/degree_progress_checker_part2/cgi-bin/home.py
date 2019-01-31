#!/usr/bin/env python3

# home page script
import mysql.connector
import cgitb, cgi

def main():
	cgitb.enable()
	form = cgi.FieldStorage()
	user_id = form['userID'].value
	user_pw = form['password'].value

	register = False
	login = False

	if "register" in form:
		register = True
	elif "login" in form:
		login = True

# prewritten sql commands
	allInfo_sql = 'select * from login'
	check_sql = 'select * from login where userid = %s and password = %s'
	insert_sql = 'insert into login (userid, password, visits) values (%s, %s, 1)'
	update_sql = 'update login set visits = visits + 1 where userid=%s and password=%s'

# connect to database
	cnx = mysql.connector.connect(user='root',
								  password='password',
								  database='cst363',
								  host='127.0.0.1')

	"""cnx = mysql.connector.connect(user='csumb_dev_rw',
                                password='hahaottershaha',
                                database='cst363',
                                host='csumb-dev.cz9b0xhrnfv1.us-east-1.rds.amazonaws.com')"""

	cursor = cnx.cursor()
	cursor.execute(check_sql, (user_id, user_pw))
	row = cursor.fetchone()
	cursorAll = cnx.cursor()
	cursorAll.execute(allInfo_sql)
	allUserIDs = cursorAll.fetchall()
	
	studentURL = "/student.html"
	studentRegURL = "/cgi-bin/student-registration.py"
	adminURL = "/admin.html"
	
	adminErr = 'Invalid selection. Go back and select "login"'
	takenErr = 'This userID has been taken. Go back and try a different userID.'
	duplicateErr = 'You have already registered. Go back and select "login".'
	unregErr = 'Either you have not registered or used the wrong userID/password combination. \
				If you are registering, go back and select the register button with the same credentials. \
				Otherwise, double check the userID and/or password.'

	if login is True:
		if row is None: 
		# do nothing, need to register before logging in
			errorMSG(unregErr)

		# only admins have an userID that starts with "A_00"
		currentID = row[0]
		check = currentID[0:4]
		if check == "A_00":
			visit_number = row[2] + 1
			cursorb = cnx.cursor()
			cursorb.execute(update_sql, (user_id, user_pw))
			redirectTo(adminURL, user_id)
			return
		# anything else is a student returning
		else:
			cursorb = cnx.cursor()
			cursorb.execute(update_sql, (user_id, user_pw))
			redirectTo(studentURL, user_id)
			return
	elif register is True:
		for id in allUserIDs:
			currentID = id[0]
			currentPW = id[1]
			check = currentID[0:4]
			if currentID == user_id:
				if check == "A_00":
					errorMSG(adminErr)
					return
				elif currentPW == user_pw:
					errorMSG(takenErr)
					return
				else:
					errorMSG(duplicateErr)
					return
		if row is None:
			cursorb = cnx.cursor()
			cursorb.execute(insert_sql, (user_id, user_pw))
			redirectTo(studentRegURL, user_id)
			return

	cnx.commit()
	cnx.close()

def errorMSG(msg):
	print("Content-type: text/html")
	print()
	print("<html><body>")
	print('<br> <a href="/home.html">%s</a>' % msg)
	print("</html></body>")
	
def redirectTo(redirectURL,cookie):
	print("Set-Cookie:userid = " + cookie + ";")
	print("Content-type: text/html")
	#print("Location: %s" % redirectURL)
	print()
	print("<html>")
	print("<head>")
	print('<meta http-equiv="refresh" content="0;url=%s" />' % redirectURL)
	print("<title>You are going to be redirected</title>")
	print("</head>")
	print("<body>")
	print('Redirecting...<a href="%s">Click here if you are not redirected</a>' % redirectURL)
	print("</body>")
	print("</html>")
	
main()