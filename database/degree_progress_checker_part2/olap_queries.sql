-- 1. Retrieve user manifest, with number of units and classes taken
SELECT
	userid
    , SUM(course_units) units_taken
    , COUNT(*) courses_taken
FROM cst363.master_view
GROUP BY 1 WITH ROLLUP

-- 2. Retrieve the most taken classes at the college
SELECT
	course_code
    , COUNT(*) students_taken
FROM cst363.master_view
GROUP BY 1
ORDER BY 2 DESC

-- 3. Retrieve most popular course areas
SELECT
	course_area
    , area_description
    , SUM(course_units) total_units
    , COUNT(*) courses_taken
FROM cst363.master_view
GROUP BY 1, 2
ORDER BY 3 DESC, 2 DESC

-- 4. Retrieve student's most frequented course areas
SELECT
	userid
    , area_description
    , SUM(course_units) total_units
    , COUNT(*) courses_taken
FROM cst363.master_view
GROUP BY 1, 2
ORDER BY 1, 3 DESC, 2 DESC

-- 5. Retrieve the most unpopular course areas with a count of how many classes that were never taken
SELECT
	c.course_area
    , ccad.area_description
    , COUNT(*) number_of_classes_not_taken_once
FROM cst363.cc_courses c
LEFT JOIN cst363.master_view m
	USING(course_code)
JOIN cst363.cc_area_description ccad
	ON c.course_area = ccad.course_area
WHERE m.course_code IS NULL
GROUP BY 1, 2
ORDER BY 3 DESC