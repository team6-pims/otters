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
	fetchCompleteClasses = 'select complete_classes from login where userid = %s'
	fetchCatTitleID = 'select course_code, course_title, course_area, course_units from cc_courses where course_id = %s'
	
	# create header and page
	print("Content-type: text/html")
	print()
	print('<html><head>')
	print('<TITLE>Degree Progress</TITLE></head>')
	print('<body>')
	print('<h2> Welcome back %s, </h2>' % user_id)
	print('<br>Here are the classes you have completed:')

	try:
		# connect to database
		cnx = mysql.connector.connect(user='root',
										password='password',
										database='cst363',
										host='127.0.0.1')

		print('<form action="./student.py" method="post">')

		getUserClasses = cnx.cursor()
		getUserClasses.execute(fetchCompleteClasses, (user_id,))
		userClasses = getUserClasses.fetchone()
		fullCourseInfo = []
		sectionA = ['A', 0]
		sectionB = ['B', 0]
		sectionC = ['C', 0]
		sectionD = ['D', 0]
		sectionE = ['E', 0]

		if userClasses[0] is None:
			print("<p>You haven't completed any classes. Please follow the link below to select your classes.")
		else:
			classIDs = userClasses[0].split(",")
			getCatTitle = cnx.cursor()

			# create list of all completed classes
			for course in classIDs:
				getCatTitle.execute(fetchCatTitleID, (course,))
				fullCourseInfo.append(getCatTitle.fetchone())

			print('<table>')
			print('<tr> <th>Course Code</th> <th>Course Title</th> <th>Sub-division</th> <th>Units</th> </tr>')
			for course in fullCourseInfo:
				if course is not None:
					print('<tr><td>%s<td>%s<td>%s<td>%s</tr>' % (course[0], course[1], course[2], str(course[3])))
					courseAreaTag = course[2]
					# find unit counts in each sub-division
					if courseAreaTag[0] == 'A':
						sectionA[1] += course[3]
					elif courseAreaTag[0] == 'B':
						sectionB[1] += course[3]
					elif courseAreaTag[0] == 'C':
						sectionC[1] += course[3]
					elif courseAreaTag[0] == 'D':
						sectionD[1] += course[3]
					elif courseAreaTag[0] == 'E':
						sectionE[1] += course[3]
			print('</table>')

		print('<br><br><p>Here are the remaining categories you have unfulfilled in your degree</p>')
		print('<table>')
		print('<tr> <th>Sub-division</th> <th>Remaining units</th> </tr>')

		if (9 - sectionA[1]) > 0:
			print('<tr><td>%s <td>%s </tr>' % (sectionA[0], str(9 - sectionA[1])))
		else:
			print('<tr><td>Section A requirements fulfilled. </tr>')
		if (12 - sectionB[1]) > 0:
			print('<tr><td>%s <td>%s </tr>' % (sectionB[0], str(12 - sectionB[1])))
		else:
			print('<tr><td>Section B requirements fulfilled. </tr>')
		if (12 - sectionC[1]) > 0:
			print('<tr><td>%s <td>%s </tr>' % (sectionC[0], str(12 - sectionC[1])))
		else:
			print('<tr><td>Section C requirements fulfilled. </tr>')
		if (12 - sectionD[1]) > 0:
			print('<tr><td>%s <td>%s </tr>' % (sectionD[0], str(12 - sectionD[1])))
		else:
			print('<tr><td>Section D requirements fulfilled. </tr>')
		if (1 - sectionE[1]) > 0:
			print('<tr><td>%s <td>%s </tr>' % (sectionE[0], str(1 - sectionE[1])))
		else:
			print('<tr><td>Section E requirements fulfilled. </tr>')
		print('</table>')

		print('<p>If you wish to enter new classes that you have completed, please push the button below. \
				Otherwise, you may select "logoff" to leave this page.</p>')

		print('<a href="/update-classes.html">Update Classes</a>')
		#print('<input type="submit" name="logoff" value="Log Off"/>')

	except mysql.connector.Error as err:
		print("ERROR", err)

	finally:
		cnx.close()  # close the connection


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