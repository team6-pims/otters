#!/usr/bin/env python3

# student registration

import mysql.connector
import cgitb
import cgi
from os import environ


def main():
	cgitb.enable()
	form = cgi.FieldStorage()

	submit = False

	if "submit" in form:
		submit = True
	
	# retrieve userid
	if 'HTTP_COOKIE' in environ:
		for cookie in environ['HTTP_COOKIE'].split(";"):
			(key, value) = cookie.split("=")
			if key == "userid":
				user_id = value
	
	print('Content-Type: text/html')    # HTML is following
	print()                             # blank line, end of headers
	print()
	print('<html><head>')
	print('<TITLE>Degree Progress</TITLE>')
	print('<body>')
	print('<p>If this is your first time visiting this page, you must select the classes \
	you have taken so far. If you are returning, the classes you have taken are in bold. \
	Once you are finished, click submit to be taken to the default \
	home page.<br><br>If you have not taken a class for the category, do not select a class.</p>')

	grabAllCategories = 'select course_area from cc_area_description'
	grabClassesByType = 'select course_code, course_title, course_id from cc_courses where course_area = %s group by \
						course_code, course_title, course_id'
	grabCatDescription = 'select course_area, area_description from cc_area_description where course_area = %s'
	getClassesSQL = 'select course_id from course_history where userid = %s'


	# connect to database
	try:
		cnx = mysql.connector.connect(user='root',
									password='password',
									database='cst363',
									host='127.0.0.1')

		"""cnx = mysql.connector.connect(user='csumb_dev_rw',
									password='hahaottershaha',
									database='cst363',
									host='csumb-dev.cz9b0xhrnfv1.us-east-1.rds.amazonaws.com')"""

		# grab selected classes from page. returns a list of strings
		selectedClasses = form.getlist("classid[]")

		# create cursor to grab category descriptions
		categoryCursor = cnx.cursor(buffered=True)
		categoryCursor.execute(grabAllCategories)
		allCategories = categoryCursor.fetchall()

		# create cursors that populate the lists
		categoryDescriptions = cnx.cursor(buffered=True)
		classNames = cnx.cursor(buffered=True)

		# create cursor to grab saved completed classes that a returning user has saved
		# if a new user, cursor query will return an empty list
		allComplete = cnx.cursor(buffered=True)
		allComplete.execute(getClassesSQL, (user_id,))
		allClasses = allComplete.fetchall()
		# allClasses is a list of single value tuples. Need to covert to list of strings
		completedClasses = []
		for unit in allClasses:
			completedClasses.append(str(unit[0]))

		print('<form action="./student-registration.py" method="post">')

		if submit is True:
			# save into database the courseIDs as a string
			insertClassesSQL = 'insert into course_history values (%s, %s)'
			deleteClassSQL = 'delete from course_history where (userid = %s and course_id = %s)'
			URL = 'http://localhost:8000/student.html'

			setClasses = cnx.cursor(buffered=True)
			deleteClasses = cnx.cursor(buffered=True)

			if not selectedClasses:  # no selected classes, nothing to commit, go to next page
				redirectTo(URL, user_id)
				return
			elif not completedClasses:  # new user, commit all first choices
				for course_id in seletedClasses:
					setClasses.execute(insertClassesSQL, (user_id, int(course_id)))
				cnx.commit()
				redirectTo(URL, user_id)
				return
			if len(completedClasses) > len(selectedClasses):  # removing a class from list
				print(selectedClasses, completedClasses)
				# convert saved data into list
				for course_id in selectedClasses:
					completedClasses.remove(course_id)  # gives list of unselected classes

				for course_id in completedClasses:  # delete all unselected classes. id is string, convert to int
					deleteClasses.execute(deleteClassSQL, (user_id, int(course_id)))

				cnx.commit()
				redirectTo(URL, user_id)
				return
			else:						  # adding to class list, returning user
				for course_id in selectedClasses:
					if course_id in completedClasses:
						continue
					else:
						setClasses.execute(insertClassesSQL, (user_id, int(course_id)))
				cnx.commit()
				redirectTo(URL, user_id)
				return

		# populate the class list. Returning users previous selected classes are in bold
		for category in allCategories:
			# fetch three class IDs at a time
			classNames.execute(grabClassesByType, category)
			threeClasses = classNames.fetchmany(size=3)

			# fetch the category descriptions which contain headers and sub division descriptions.
			categoryDescriptions.execute(grabCatDescription, category)
			catDesc = categoryDescriptions.fetchone()

			if len(category[0]) == 1 and category[0] != 'E':
				print('<h2>%s</h2>' % catDesc[1])  # title of category
				continue
			elif category[0] == 'E':  # special requirements, no sub divisions.
				print('<h2>%s</h2>' % catDesc[1])
				print('<fieldset>')
				print('<table>')
				print('<h4>%s: %s</h4>' % (catDesc[0], catDesc[1]))
				while len(threeClasses) > 0:
					createMenuRow(threeClasses, completedClasses)  # write all rows as check list
					threeClasses = classNames.fetchmany(size=3)  # grab the next row
				print('</table>')
				print('</fieldset>')
			else:
				print('<fieldset>')
				print('<table>')
				print('<h4>%s: %s</h4>' % (catDesc[0], catDesc[1]))
				while len(threeClasses) > 0:
					createMenuRow(threeClasses, completedClasses)  # write all rows as check list
					threeClasses = classNames.fetchmany(size=3)  # grab the next row
				print('</table>')
				print('</fieldset>')
		
		print('<div><br>')
		print('<input type="submit" name="submit" value="Finish"/>')
		print('</div></form>')

	except mysql.connector.Error as err:
		print("ERROR", err)

	finally:
		cnx.close()  # close the connection


def createMenuRow(cla, all):
	# convert list of single value tuples to simple list

	print('<tr>')
	print('<div>')
	for oneClass in cla:
		if oneClass is not None:
			if str(oneClass[2]) in all:
				label = oneClass[0] + ", " + oneClass[1]
				print('<td><b>')
				print('<input type="checkbox" name="classid[]" id="%s" value="%s" checked>' % (oneClass[1], oneClass[2]))
				print('<label for="%s">%s</label></b>' % (oneClass[1], label))
			else:
				label = oneClass[0] + ", " + oneClass[1]
				print('<td>')
				print('<input type="checkbox" name="classid[]" id="%s" value="%s">' % (oneClass[1], oneClass[2]))
				print('<label for="%s">%s</label>' % (oneClass[1], label))
		else:
			return
	print('</tr>')
	print('</div>')


def redirectTo(redirectURL,cookie):
	print("Set-Cookie:userid = "+cookie+";")
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