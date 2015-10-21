/* Patrick McNamara */
/* cs4410 HW 1 */
/* 09/28/2015 */

#include<time.h>
#include<iostream>
using namespace std;

#include <windows.h>
#include<gl/GL.h>
#include<gl/GLU.h>
#include<gl/glut.h>

/* Since the checkerboard should be square, we only need one dimension. */
const int SCREEN_SIZE = 600;

// Init's the openGL system.
void init() {
	glClearColor(1.0, 1.0, 1.0, 0.0);
	glColor3f(0.0f, 0.0f, 0.0f);
	glPointSize(10.0);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluOrtho2D(0.0, SCREEN_SIZE, 0.0, SCREEN_SIZE);
}

// Draw's the checkerboard with squares of size pixels.
void checkerboard(int size) {
	// The two colors are stored in arrays in RGB format.
	GLint color1[3];
	GLint color2[3];
	GLint *currentColor = color1;

	// Initialize random color arrays.
	srand(time(NULL));
	for (int i = 0; i < 3; i++) {
		color1[i] = rand() % 256;
	}
	for (int i = 0; i < 3; i++) {
		color2[i] = rand() % 256;
	}

	glClear(GL_COLOR_BUFFER_BIT);
	glPushMatrix();
	for (int i = 0; i < 8; i++) {
		glPushMatrix();
		for (int j = 0; j < 8; j++) {
			// Set color and draw a single square.
			glColor3ub(currentColor[0], currentColor[1], currentColor[2]);
			glRecti(0, 0, size, size);

			// Update to prepair to draw the next square.
			glTranslated(size, 0, 0);
			// Switch the color.
			if (currentColor == color1)
				currentColor = color2;
			else
				currentColor = color1;
		}
		glPopMatrix();
		glTranslated(0, size, 0);
		if (currentColor == color1)
			currentColor = color2;
		else
			currentColor = color1;
	}
	glPopMatrix();
	glFlush();
}

void draw() {
	checkerboard(SCREEN_SIZE / 8);
}

int main(int argc, char **argv) {
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
	glutInitWindowSize(SCREEN_SIZE, SCREEN_SIZE);
	glutInitWindowPosition(100, 150);
	glutCreateWindow("Checkerboard");

	glutDisplayFunc(draw);
	init();
	glutMainLoop();

}

