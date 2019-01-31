#!/usr/bin/env python3

# student landing

import mysql.connector
import cgitb, cgi
from os import environ

def main():
	cgitb.enable()
	form = cgi.FieldStorage()

	# retrive userid
	if 'HTTP_COOKIE' in environ:
		for cookie in environ['HTTP_COOKIE'].split(";"):
			(key, value) = cookie.split("=")
			if key == "userid":
				user_id = value

	regisURL = 'http://127.0.0.1:8000/update-classes.html'
	logoffURL = 'http://127.0.0.1:8000/logoff.html'

	# sql commands
	fetchCompleteClasses = 'select course_id from course_history where userid = %s'
	fetchCatTitleID = 'select course_code, course_title, course_area, course_units from cc_courses where course_id = %s'
	
	# create header and page
	print("Content-type: text/html")
	print()
	print('<html><head>')
	print('<TITLE>Degree Progress</TITLE></head>')
	print('<body>')
	print('<h2> Welcome %s, </h2>' % user_id)
	print('<h4>Here are the classes you have completed:</h4>')

	try:
		# connect to database
		cnx = mysql.connector.connect(user='root',
									  password='password',
									  database='cst363',
									  host='127.0.0.1')

		"""cnx = mysql.connector.connect(user='csumb_dev_rw',
                                    password='hahaottershaha',
                                    database='cst363',
                                    host='csumb-dev.cz9b0xhrnfv1.us-east-1.rds.amazonaws.com')"""

		print('<form action="./student.py" method="post">')

		getUserClasses = cnx.cursor()
		getUserClasses.execute(fetchCompleteClasses, (user_id,))
		userClasses = getUserClasses.fetchall()
		fullCourseInfo = ['A', 'B', 'C', 'D', 'E']
		sectionA = []
		sectionB = []
		sectionC = []
		sectionD = []
		sectionE = []

		# if student has classes
		if userClasses:
			classIDs = []
			for unit in userClasses:
				classIDs.append(unit[0])

			getCatTitle = cnx.cursor()

			# create lists in area divisions of all completed classes
			for course in classIDs:
				getCatTitle.execute(fetchCatTitleID, (course,))
				courseInfo = getCatTitle.fetchone()
				courseAreaTag = courseInfo[2]
				if courseAreaTag[0] == 'A':
					sectionA.append(courseInfo)
				elif courseAreaTag[0] == 'B':
					sectionB.append(courseInfo)
				elif courseAreaTag[0] == 'C':
					sectionC.append(courseInfo)
				elif courseAreaTag[0] == 'D':
					sectionD.append(courseInfo)
				elif courseAreaTag[0] == 'E':
					sectionE.append(courseInfo)

			# this loop prints a sub header for each section. For section, check if there are any classes completed
			# in that section. If yes, call the function to print out the table and save the sum to be used in the
			# second section
			for section in fullCourseInfo:
				if section == 'A':
					print('<b>Section A courses completed: </b>')
					if sectionA:
						sumA = enterTableRows(sectionA)
						print('<br>Total units completed: <b>%d/9</b><br>' % sumA)
						remainingSubCredits(sectionA, section)
					else:
						print('<br><i>No courses completed in this section. </i>')
						sumA = 0
						print('<br>Remaining units: %d<br>' % (9 - sumA))
				elif section == 'B':
					print('<br><b>Section B courses completed: </b>')
					if sectionB:
						sumB = enterTableRows(sectionB)
						print('<br>Total units: <b>%d/12</b><br>' % sumB)
						remainingSubCredits(sectionB, section)
					else:
						print('<br><i>No courses completed in this section. </i>')
						sumB = 0
						print('<br>Remaining units: %d<br>' % (12 - sumB))
				elif section == 'C':
					print('<br><b>Section C courses completed: </b>')
					if sectionC:
						sumC = enterTableRows(sectionC)
						print('<br>Total units: <b>%d/12</b><br>' % sumC)
						remainingSubCredits(sectionC, section)
					else:
						print('<br><i>No courses completed in this section. </i>')
						sumC = 0
						print('<br>Remaining units: %d<br>' % (12 - sumC))
				elif section == 'D':
					print('<br><b>Section D courses completed: </b>')
					if sectionD:
						sumD = enterTableRows(sectionD)
						print('<br>Total units: <b>%d/12</b><br>' % sumD)
						remainingSubCredits(sectionD, section)
					else:
						print('<br><i>No courses completed in this section. </i>')
						sumD = 0
						print('<br>Remaining units: %d<br>' % (12 - sumD))
				elif section == 'E':
					print('<br><b>Section E courses completed: </b>')
					if sectionE:
						sumE = enterTableRows(sectionE)
						print('<br>Remaining units: <b>%d</b><br>' % sumE)
						remainingSubCredits(sectionE, section)
					else:
						print('<br><i>No courses completed in this section. </i>')
						sumE = 0
						print('<br>Remaining units: %d' % (1 - sumE))

		else:
			print("<p><b>You haven't registered any classes. Please follow the link below to select your classes. \
			</b></p>")

		print('<p>If you wish to enter new classes that you have completed, please push the button below. \
				Otherwise, you may select "logoff" to leave this page.</p>')

		print('<a href="/update-classes.html">Update Classes</a>')
		#print('<input type="submit" name="logoff" value="Log Off"/>')

	except mysql.connector.Error as err:
		print("ERROR", err)

	finally:
		cnx.close()  # close the connection


def remainingSubCredits(section, category):
	A = ['A1', 'A2', 'A3']
	B = ['B1', 'B2', 'B3', 'B4']
	C = ['C1', 'C2']

	# create a dictionary of sub-division:unit total
	sum = {}
	for course in section:
		areaTag = course[2]
		if areaTag in sum:
			sum[areaTag] += course[3]
		else:
			sum[areaTag] = course[3]

	if category == 'A':
		for sub in A:
			if sub in sum and sum[sub] >= 3:
				print('<i>Sub-division %s has been satisfied.</i><br>' % sub)
			else:
				print('<i>Sub-division %s has not been satisfied.</i><br>' % sub)
	elif category == 'B':
		for sub in B:
			if sub in sum  and sum[sub] >= 3:
				print('<i>Sub-division %s has been satisfied.</i><br>' % sub)
			elif sub in sum and sub == 'B3':
				if sum['B1'] >= 3 or sum['B2'] >= 3:
					print('<i>Sub-division %s has been satisfied.</i><br>' % sub)
			else:
				print('<i>Sub-division %s has not been satisfied.</i><br>' % sub)
	elif category == 'C':
		sumC = 0
		for sub in C:
			if sub in sum and sum[sub] >= 3:
				print('<i>Sub-division %s has been satisfied.</i><br>' % sub)
			else:
				print('<i>Sub-division %s has not been satisfied.</i><br>' % sub)
			if sub in sum:
				sumC += sum[sub]
		if sumC == 12:
			return
		elif sumC < 12 and sumC > 0:
			print('<b>Division under unit requirement! Take another class to exceed 12 units</b><br>')
	elif category == 'D':
		allTags = sum.keys()
		if len(allTags) >= 3:
			print('<i>Sub-division %s has been satisfied.</i><br>' % tags[0])
		elif len(allTags) == 2:
			print('<i>Missing one sub-division satisfactory class from any section other than')
			for tag in sum:
				print(' %' % tag)
			print('.</i><br>')
		elif len(allTags) == 1:
			print('Missing two sub-division satisfactory classes from any section other than')
			for tag in sum:
				print(' %' % tag)
			print('.</i><br>')
		elif tags not in categories:
			print('<i>Sub-division %s has not been satisfied.</i><br>' % tags)
	elif category == 'E':
		if 'E' in sum and sum['E'] >= 3:
			print('<i>Division has been satisfied.</i><br>')
		elif sum['E'] < 3:
			print('<b>Division under unit requirement! Take another class to exceed 3 units.</b><br>')


def enterTableRows(courseInfo):
	subUnitSum = 0
	print('<table>')
	print('<tr> <th>Course Code</th> <th>Course Title</th> <th>Sub-division</th> <th>Units</th> </tr>')
	for course in courseInfo:
		print('<tr><td>%s<td>%s<td>%s<td>%s</tr>' % (course[0], course[1], course[2], str(course[3])))
		subUnitSum += course[3]
	print('</table>')
	return subUnitSum


def redirectTo(redirectURL, cookie):
	print("Set-Cookie:userid = "+cookie+";")
	print("Content-type: text/html")
	print("Location: %s" % redirectURL)
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