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
const int SCREEN_HEIGHT = SCREEN_WIDTH;
const int SCREEN_MAX_RIGHT = SCREEN_WIDTH / 2;
const int SCREEN_MAX_LEFT = -SCREEN_WIDTH / 2;
const int SCREEN_MAX_TOP = SCREEN_HEIGHT / 2;
const int SCREEN_MAX_BOTTOM = -SCREEN_HEIGHT / 2;

const int NUM_HEX = 30; // Number of hexagons to draw.
const int MIN_HEX_SIZE = 20; //The size of the smallest hexagon

const float ZOOM_FACTOR = .05; // The higher this is, the fatster it zooms.

struct GLintPoint {
	GLint x, y;
};

// Init the openGL system.
void init() {
	glClearColor(1.0, 1.0, 1.0, 1.0);
	glColor3f(0.0f, 0.0f, 0.0f);
	glLineWidth(1.0);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluOrtho2D(SCREEN_MAX_LEFT, SCREEN_MAX_RIGHT, SCREEN_MAX_BOTTOM, SCREEN_MAX_TOP);
}

// Draws a hexagon that has a side length of size
void drawHexagon(int size) {
	glPushMatrix();
	for (int i = 0; i < 6; i++) {
		glBegin(GL_LINES);
		glVertex2i(-size*cos(60*M_PI/180), size*sin(60*M_PI/180));
		glVertex2i( size*cos(60*M_PI/180), size*sin(60*M_PI/180));
		glEnd();

		glRotated(60.0, 0, 0, 1);
	}
	glPopMatrix();
}

// Draws the swirling polygon structure
void drawSwirl() {
	glPushMatrix();
	
	// Find out the difference in size between each hexagon
	int hexSizeDiff = ((SCREEN_WIDTH / 2) - (MIN_HEX_SIZE / 2)) / NUM_HEX;
	// Draw each hexagon
	for (int i = 0; i < NUM_HEX; i++) {
		drawHexagon(MIN_HEX_SIZE + (i * hexSizeDiff));
		glRotated(-5.0, 0, 0, 1);
	}

	glFlush();
	glPopMatrix();
	glutSwapBuffers();
}

// Set a new window size
void setWindow(GLdouble left, GLdouble right, GLdouble bottom, GLdouble top) {
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluOrtho2D(left, right, bottom, top);
	glMatrixMode(GL_MODELVIEW);
}

void draw() {
	glClear(GL_COLOR_BUFFER_BIT);
	static bool zoomIn;
	static float left, right, bottom, top;
	// Initialize static variables
	if (left == 0) {
		left = SCREEN_MAX_LEFT, right = SCREEN_MAX_RIGHT, bottom = SCREEN_MAX_BOTTOM, top = SCREEN_MAX_TOP;
		zoomIn = true;
	}

	// Should you zoom in further?
	if (zoomIn && left <= -MIN_HEX_SIZE) {
		setWindow(left, right, bottom, top);
		left += ZOOM_FACTOR;
		right -= ZOOM_FACTOR;
		bottom += ZOOM_FACTOR;
		top -= ZOOM_FACTOR;
		glutPostRedisplay();
	} else if (zoomIn) { // Maximum zoom, start zooming out
		zoomIn = false;
	// Should you zoom out further?
	} else if (!zoomIn && left > SCREEN_MAX_LEFT) {
		setWindow(left, right, bottom, top);
		left -= ZOOM_FACTOR;
		right += ZOOM_FACTOR;
		bottom -= ZOOM_FACTOR;
		top += ZOOM_FACTOR;
		glutPostRedisplay();
	} else if (!zoomIn) { // Minimum zoom, start zooming in
		zoomIn = true;
	}
}

void keypress(unsigned char key, int x, int y) {
	if (key == 'q') {
		exit(0);
	}
}

int main(int argc, char **argv) {
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB);
	glutInitWindowSize(SCREEN_WIDTH, SCREEN_HEIGHT);
	glutInitWindowPosition(100, 150);
	glutCreateWindow("Whirling Hexagons");
	glutDisplayFunc(drawSwirl);
	glutKeyboardFunc(keypress);
	glutIdleFunc(draw);
	init();
	glutMainLoop();
}

