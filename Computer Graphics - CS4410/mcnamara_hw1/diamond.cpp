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

// Number of diamonds to draw.
const int num_polys = 10;

struct GLintPoint {
	GLint x, y;
};

// Init the openGL system.
void init() {
	glClearColor(1.0, 1.0, 1.0, 1.0);
	//glColor3f(0.0f, 0.0f, 0.0f);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluOrtho2D(0.0, SCREEN_WIDTH, 0.0, SCREEN_HEIGHT);
}

// Draws a diamond that is size * 2 pixles wide at center
void drawDiamond(GLintPoint center, int size) {
	glBegin(GL_POLYGON);
	glVertex2d(center.x + size, center.y);
	glVertex2d(center.x, center.y - size);
	glVertex2d(center.x - size, center.y);
	glVertex2d(center.x, center.y + size);
	glEnd();
	glFlush();
}

void draw() {
	glClear(GL_COLOR_BUFFER_BIT);
	srand(time(NULL));

	for (int i = 0; i < num_polys; i++) {
		glColor3ub(rand() % 265, rand() % 265, rand() % 265); // Give this diamond a random color
		// Make sure diamond's center is on screen and its size is between 25 and 125
		drawDiamond({ rand() % SCREEN_WIDTH, rand() % SCREEN_HEIGHT }, (rand() % 100) + 25); 
	}
}

int main(int argc, char **argv) {
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
	glutInitWindowSize(SCREEN_WIDTH, SCREEN_HEIGHT);
	glutInitWindowPosition(100, 150);
	glutCreateWindow("Diamond");
	glutDisplayFunc(draw);
	init();
	glutMainLoop();
}

