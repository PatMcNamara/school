/* Patrick McNamara */
/* cs4410 HW 1 */
/* 10/01/2015 */
#define _USE_MATH_DEFINES
#include<math.h>

#include <windows.h>
#include<gl/GL.h>
#include<gl/GLU.h>
#include<gl/glut.h>

const int SCREEN_WIDTH = 640;
const int SCREEN_HEIGHT = 480;

struct GLfloatPoint {
	GLfloat x, y;
};

// Init the openGL system.
void init() {
	glClearColor(1.0, 1.0, 1.0, 1.0);
	glColor3f(0.0, 0.0, 0.0);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluOrtho2D(0.0, SCREEN_WIDTH, 0.0, SCREEN_HEIGHT);
}

// calculates a third of the way between start and end.
inline int midThird(int start, int end) {
	return (end - start) / 3;
}

// Recursive function to draw one side of Koches snowflake.
void drawSnowflakeSide(int n, GLfloatPoint start, GLfloatPoint end) {
	// if n=0 then you just draw a line from the start to the end
	if (n == 0) {
		glBegin(GL_LINES);
		glVertex2f(start.x, start.y);
		glVertex2f(end.x, end.y);
		glEnd();
		return;
	}
	// Split the line into thirds.
	GLfloatPoint firstThirdPoint = { midThird(start.x, end.x) + start.x, midThird(start.y, end.y) + start.y };
	GLfloatPoint lastThirdPoint = { end.x - midThird(start.x, end.x), end.y - midThird(start.y, end.y) };
	// Find the length of the one third lines
	float thirdLength = sqrt(pow(lastThirdPoint.x - firstThirdPoint.x, 2) + pow(lastThirdPoint.y - firstThirdPoint.y, 2));
	// Calculate the standard position of the line
	float angle = atan2(lastThirdPoint.y - firstThirdPoint.y, lastThirdPoint.x - firstThirdPoint.x) * 180 / M_PI;

	const float RadPerDeg = 0.01745;
	// Find the point at the apex of the triangle to be created in the middle third
	float x = firstThirdPoint.x + (thirdLength * cos(RadPerDeg * (angle - 60)));
	float y = firstThirdPoint.y + (thirdLength * sin(RadPerDeg * (angle - 60)));
	GLfloatPoint middleTriangle = { x, y };

	// Recursively call both end thirds.
	drawSnowflakeSide(n - 1, start, firstThirdPoint);
	drawSnowflakeSide(n - 1, lastThirdPoint, end);
	// Recursively call the middle third triangle.
	drawSnowflakeSide(n - 1, firstThirdPoint, middleTriangle);
	drawSnowflakeSide(n - 1, middleTriangle, lastThirdPoint);
}

void draw() {
	glClear(GL_COLOR_BUFFER_BIT);
}

void keypress(unsigned char key, int x, int y) {
	if (key >= '0' && key <= '9') {
		glClear(GL_COLOR_BUFFER_BIT);
		// Convert the keystroke to a decimal
		int n = key - 48;

		// Draw each side of the snowflake independently.
		drawSnowflakeSide(n, { 100, 125 }, { 500, 125 });
		drawSnowflakeSide(n, { 500, 125 }, { 300, 125 + ((float)sqrt(3) * 200) });
		drawSnowflakeSide(n, { 300, 125 + ((float)sqrt(3) * 200) }, { 100, 125 });
		glFlush();
	}
}

int main(int argc, char **argv) {
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
	glutInitWindowSize(SCREEN_WIDTH, SCREEN_HEIGHT);
	glutInitWindowPosition(100, 150);
	glutCreateWindow("Diamond");
	glutDisplayFunc(draw);
	glutKeyboardFunc(keypress);
	init();
	glutMainLoop();
}

