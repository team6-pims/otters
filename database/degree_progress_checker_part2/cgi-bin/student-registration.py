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
	print('<html><head>')
	print('<TITLE>Update Classes</TITLE>')
	print('<body><h3>Important!</h3>')
	print('<p>If this is your first time visiting this page, you must select the classes \
	you have taken so far. If you are returning, the classes you have taken are already selected. \
	Once you are finished, click submit to be taken to the summary page.<br><i>If you have not \
	taken a class for the category, do not select a class.</i></p>')

	grabAllCategories = 'select course_area from cc_area_description'
	grabClassesByType = 'select course_code, course_title, course_id from cc_courses where course_area = %s group by \
						course_code, course_title, course_id'
	grabCatDescription = 'select course_area, area_description from cc_area_description where course_area = %s'
	getCompletedClasses = 'select course_id from course_history where userid = %s'

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
		allComplete.execute(getCompletedClasses, (user_id,))
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
			elif len(selectedClasses) > len(completedClasses):  # adding to class list, returning user
				for course_id in selectedClasses:
					if course_id in completedClasses:
						continue
					else:
						setClasses.execute(insertClassesSQL, (user_id, int(course_id)))
				cnx.commit()
				redirectTo(URL, user_id)
				return
			elif len(completedClasses) > len(selectedClasses):  # removing a class from list
				print(selectedClasses, completedClasses)
				# convert saved data into list
				for course_id in selectedClasses:
					completedClasses.remove(course_id)  # gives list of unselected classes

				for course_id in completedClasses:  # delete all unselected classes. id is string, convert to int
					deleteClasses.execute(deleteClassSQL, (user_id, int(course_id)))

				cnx.commit()
				redirectTo(URL, user_id)
				return
			else:			  # new user, commit all first choices
				for course_id in selectedClasses:
					setClasses.execute(insertClassesSQL, (user_id, int(course_id)))
				cnx.commit()
				redirectTo(URL, user_id)
				return

		# populate the class list. Returning users previous selected classes are in bold
		for category in allCategories:
			# fetch all class info in category
			classNames.execute(grabClassesByType, category)
			allClasses = classNames.fetchall()

			# fetch the category descriptions which contain headers and sub division descriptions.
			categoryDescriptions.execute(grabCatDescription, category)
			catDesc = categoryDescriptions.fetchone()

			if len(category[0]) == 1 and category[0] != 'E':
				print('<h2>%s</h2>' % catDesc[1])  # title of category
				continue
			elif category[0] == 'E':  # special requirements, no sub divisions.
				print('<h2>%s</h2>' % catDesc[1])
				print('<div><b>%s: %s</b><br>' % (catDesc[0], catDesc[1]))
				createPulldownMenu(allClasses, completedClasses, False)  # write all rows as check list
			elif category[0] == 'C1':  # 4 classes out of two sub divisions
				print('<div><h4>%sA: %s</b><br>' % (catDesc[0], catDesc[1]))
				createPulldownMenu(allClasses, completedClasses, False)
				print('<div><b>%sB: %s</b><br>' % (catDesc[0], catDesc[1]))
				createPulldownMenu(allClasses, completedClasses, True)
			elif category[0] == 'C2':  # 4 classes out of two sub divisions
				print('<div><b>%sA: %s</b><br>' % (catDesc[0], catDesc[1]))
				createPulldownMenu(allClasses, completedClasses, False)
				print('<div><b>%sB: %s</b><br>' % (catDesc[0], catDesc[1]))
				createPulldownMenu(allClasses, completedClasses, True)
			else:
				print('<div><b>%s: %s</b><br>' % (catDesc[0], catDesc[1]))
				createPulldownMenu(allClasses, completedClasses, False)

		# submit button
		print('<div><br>')
		print('<input type="submit" name="submit" value="Finish"/>')
		print('</div></form>')

	except mysql.connector.Error as err:
		print("ERROR", err)

	finally:
		cnx.close()  # close the connection


def createPulldownMenu(cla, allC, override):
	print('<select name="classid[]">')
	print('<option value="">--Select a class--</option>')
	for oneClass in cla:
		if (str(oneClass[2]) in allC) and (not override):
			print('<option value = "%s" selected>%s: %s</option>' % (oneClass[2], oneClass[0], oneClass[1]))
		else:
			print('<option value = "%s">%s: %s</option>' % (oneClass[2], oneClass[0], oneClass[1]))
	print('</select></div><br>')

def redirectTo(redirectURL,cookie):
	print("Set-Cookie:userid="+cookie+";")
	print("Content-type: text/html")
	print()
	print("<html><head>")
	print('<meta http-equiv="refresh" content="0;url=%s" />' % redirectURL)
	print("<title>You are going to be redirected</title>")
	print("</head><body>")
	print('Redirecting...<a href="%s">Click here if you are not redirected</a>' % redirectURL)
	print("</body></html>")


main()
