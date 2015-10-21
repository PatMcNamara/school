/* Patrick McNamara */
/* cs4410 HW 1 */
/* 10/01/2015 */

#include<time.h>

#include <windows.h>
#include<gl/GL.h>
#include<gl/GLU.h>
#include<gl/glut.h>

const int SCREEN_WIDTH = 640;
const int SCREEN_HEIGHT = 480;

const int NUM = 1000; // Number of points to remember in polyline.

struct GLintPoint {
	int x;
	int y;
};

// Init the openGL system.
void init() {
	glClearColor(1.0, 1.0, 1.0, 1.0);
	glColor3f(0.0f, 0.0f, 0.0f);
	glLineWidth(2.0);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluOrtho2D(0.0, SCREEN_WIDTH, 0.0, SCREEN_HEIGHT);
}

int last = -1; // The end of the array.
bool mousePressed = false; // Is left mouse button pressed?
void mousePress(int button, int state, int x, int y)
{
	if (button == GLUT_LEFT_BUTTON && state == GLUT_DOWN) {mousePressed = true; }
	else if (button == GLUT_LEFT_BUTTON && state == GLUT_UP) {mousePressed = false; }
	else if (button == GLUT_RIGHT_BUTTON && state == GLUT_DOWN) {
		last = -1; // reset index
		glClear(GL_COLOR_BUFFER_BIT); // clear background
		glFlush();
	}
}

void mouseHold(int x, int y) {
	static GLintPoint list[NUM]; // made static to remember last value

	if (mousePressed && last < NUM - 1) {
		list[++last].x = x;
		list[last].y = SCREEN_HEIGHT - y - 1; // flip y coordinate
		glClear(GL_COLOR_BUFFER_BIT); // clear background
		glBegin(GL_LINE_STRIP);	// redraw polyline
		for (int i = 0; i <= last; i++)
			glVertex2i(list[i].x, list[i].y);
		glEnd();
		glFlush();
	}
}


void draw() {

}

int main(int argc, char **argv) {
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
	glutInitWindowSize(SCREEN_WIDTH, SCREEN_HEIGHT);
	glutInitWindowPosition(100, 150);
	glutCreateWindow("Freehand");
	glutMouseFunc(mousePress);
	glutMotionFunc(mouseHold);
	glutDisplayFunc(draw);
	init();
	glutMainLoop();
}

