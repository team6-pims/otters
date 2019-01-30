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
	print('<p>Since this is your first time visiting this page, you must select the classes \
	you have taken so far. Once you are finished, click submit to be taken to the default \
	home page.<br><br>If you have not taken a class for the category, do not select a class.</p>')

	grabAllCategories = 'select course_area from cc_area_description'
	grabClassesByType = 'select course_code, course_title, course_id from cc_courses where course_area = %s group by \
						course_code, course_title, course_id'
	grabCatDescription = 'select course_area, area_description from cc_area_description where course_area = %s'


	# connect to database
	try:
		cnx = mysql.connector.connect(user='root',
										password='password',
										database='cst363',
										host='127.0.0.1')

		categoryCursor = cnx.cursor()
		categoryCursor.execute(grabAllCategories)
		allCategories = categoryCursor.fetchall()
		categoryDescriptions = cnx.cursor(buffered=True)
		classNames = cnx.cursor(buffered=True)

		print('<form action="./student-registration.py" method="post">')

		if submit is True:
			# save into database the courseIDs as a string
			getClassesSQL = 'select complete_classes from login where userid = %s'
			setClassesSQL = 'update login set complete_classes = %s where userid = %s'
			URL = 'http://localhost:8000/student.html'
			completedClasses = form.getlist("classid[]")

			getClasses = cnx.cursor(buffered=True)
			setClasses = cnx.cursor(buffered=True)
			getClasses.execute(getClassesSQL, (user_id,))
			prevClasses = getClasses.fetchone()


			if completedClasses is None:  # no selected classes, nothing to commit, go to next page
				redirectTo(URL, user_id)
				return
			elif prevClasses[0] is None:  # if new user, commit all first choices
				completeClassStr = ",".join(completedClasses)
				setClasses.execute(setClassesSQL, (completeClassStr, user_id))
				cnx.commit()
				redirectTo(URL, user_id)
				return


			prevClassList = prevClasses[0].split(',')

			if len(prevClassList) > len(completedClasses):  # removing a class from list
				# find what was removed
				for unit in completedClasses:
					prevClassList.remove(unit)  # gives list of unselected classes
				print(completedClasses)
				print('<br>')
				print(prevClassList)
				for unit in prevClassList:
					if unit in completedClasses:
						completedClasses.remove(unit) # updated list of complete classes
				# commit updated list
				completeClassStr = ",".join(completedClasses)
				setClasses.execute(setClassesSQL, (completeClassStr, user_id))
				cnx.commit()
				redirectTo(URL, user_id)
			else:						  # adding to class list, returning user
				allClassList = prevClassList
				for oneClass in completedClasses:
					if oneClass in prevClassList:
						continue
					else:
						allClassList.append(oneClass)
				allClassesStr = ','.join(allClassList)
				setClasses.execute(setClassesSQL, (allClassesStr, user_id))
				cnx.commit()
				redirectTo(URL, user_id)

		allComSQL = 'select complete_classes from login where userid = %s'
		allComplete = cnx.cursor(buffered=True)
		allComplete.execute(allComSQL, (user_id,))
		complete = allComplete.fetchone()

		if complete[0] is None:
			completeList = []
		else:
			completeStr = complete[0]
			completeList = completeStr.split(',')

		for category in allCategories:
			classNames.execute(grabClassesByType, category)
			categoryDescriptions.execute(grabCatDescription, category)
			catDesc = categoryDescriptions.fetchone()
			threeClasses = classNames.fetchmany(size=3)

			if len(category[0]) == 1 and category[0] != 'E':
				print('<h2>%s</h2>' % catDesc[1])  # title of category
				continue
			elif category[0] == 'E':
				print('<h2>%s</h2>' % catDesc[1])
				print('<fieldset>')
				print('<table>')
				print('<h4>%s: %s</h4>' % (catDesc[0], catDesc[1]))
				while len(threeClasses) > 0:
					createMenuRow(threeClasses, completeList)  # write all rows as check list
					threeClasses = classNames.fetchmany(size=3)  # grab the next row
				print('</table>')
				print('</fieldset>')
			else:
				print('<fieldset>')
				print('<table>')
				print('<h4>%s: %s</h4>' % (catDesc[0], catDesc[1]))
				while len(threeClasses) > 0:
					createMenuRow(threeClasses, completeList)  # write all rows as check list
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