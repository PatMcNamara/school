/* Patrick McNamara */
/* cs4410 HW 1 */
/* 10/01/2015 */

#include<time.h>
#define _USE_MATH_DEFINES
#include<math.h>

#include <windows.h>
#include<gl/GL.h>
#include<gl/GLU.h>
#include<gl/glut.h>

const int SCREEN_WIDTH = 640;
const int SCREEN_HEIGHT = 480;

const int NGON_SIZE = 50;

struct GLintPoint {
	GLint x, y;
};

// Init the openGL system.
void init() {
	glClearColor(1.0, 1.0, 1.0, 1.0);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluOrtho2D(0.0, SCREEN_WIDTH, 0.0, SCREEN_HEIGHT);
}

// a ngon at (cx, cy)
void ngon(int n, float cx, float cy, float radius, float stAngle) {
	float cur_x, cur_y;

	if (n < 3) return;
	double angle = stAngle * M_PI / 180; // initial angle
	double angleInc = 2 * M_PI / n; // angle increment

	glBegin(GL_POLYGON); // closed polyline
	for (int k = 0; k < n; k++) { // repeat n times
		cur_x = cx + radius * cos(angle);
		cur_y = cy + radius * sin(angle);
		glVertex2f(cur_x, cur_y);
		angle += angleInc;
	}
	glEnd();
	glFlush();
}

void draw() {
	glClear(GL_COLOR_BUFFER_BIT);
}

void keypress(unsigned char key, int x, int y) {
	if ((key >= 'A' && key <= 'Z') || (key >= 'a' && key <= 'z')) {
		glColor3ub(rand() % 256, rand() % 256, rand() % 256);
		ngon((rand() % 7) + 3, rand() % SCREEN_WIDTH, rand() % SCREEN_HEIGHT, NGON_SIZE, 0);
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
	srand(time(NULL)); // Initialize RNG
	glutMainLoop();
}

