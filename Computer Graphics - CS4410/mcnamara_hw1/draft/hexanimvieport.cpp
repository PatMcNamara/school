/* Patrick McNamara */
/* cs4410 HW 1 */
/* 10/01/2015 */

#include<time.h>
#define _USE_MATH_DEFINES
#include<math.h>
#include<iostream>
using namespace std;

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

const float ZOOM_FACTOR = .25; // The higher this is, the fatster it zooms.

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

void drawSwirl() {
	int hexSizeDiff = ((SCREEN_WIDTH / 2) - (MIN_HEX_SIZE / 2)) / NUM_HEX;
	for (int i = 0; i < NUM_HEX; i++) {
		drawHexagon(MIN_HEX_SIZE + (i * hexSizeDiff));
		glRotated(-5.0, 0, 0, 1);
	}
	glFlush();
	//glutPostRedisplay();
}

void draw() {
	glClear(GL_COLOR_BUFFER_BIT);
	//drawSwirl();
	//int left = SCREEN_MAX_LEFT, right = SCREEN_MAX_RIGHT, top = SCREEN_MAX_TOP, bottom = SCREEN_MAX_BOTTOM;

	/*left = bottom = 0;
	right = top = 1;
	glViewport(-100, -100, SCREEN_WIDTH +200, SCREEN_HEIGHT + 200);
	glFlush();
	drawSwirl();*/
	
	
	
	//left = bottom = 0;
	//int scale = SCREEN_HEIGHT;
	//glMatrixMode(GL_PROJECTION);
	//glLoadIdentity();
	float shift = 0;
	while (true) {
		// zoom in until your boundery reaches the smallest hex
		//while (shift > -((SCREEN_HEIGHT / 2) - (MIN_HEX_SIZE / 2))) {
		while (shift > -SCREEN_HEIGHT * 10) {
			glClear(GL_COLOR_BUFFER_BIT);
			glViewport(shift, shift, SCREEN_HEIGHT - (shift * 2), SCREEN_HEIGHT - (shift * 2));
			/*left -= ZOOM_FACTOR;
			bottom -= ZOOM_FACTOR;
			scale += ZOOM_FACTOR * 2;*/
			//glutPostRedisplay();
			shift -= ZOOM_FACTOR;
			drawSwirl();
		}
		while (shift < 0) {
			glClear(GL_COLOR_BUFFER_BIT);
			glViewport(shift, shift, SCREEN_HEIGHT - (shift * 2), SCREEN_HEIGHT - (shift * 2));
			/*left -= ZOOM_FACTOR;
			bottom -= ZOOM_FACTOR;
			scale += ZOOM_FACTOR * 2;*/
			//glutPostRedisplay();
			shift += ZOOM_FACTOR;
			drawSwirl();
		}
	}
}

int main(int argc, char **argv) {
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
	glutInitWindowSize(SCREEN_WIDTH, SCREEN_HEIGHT);
	glutInitWindowPosition(100, 150);
	glutCreateWindow("Whirling Hexagons");
	//glutDisplayFunc(draw);
	glutIdleFunc(draw);
	init();
	glutMainLoop();
}

