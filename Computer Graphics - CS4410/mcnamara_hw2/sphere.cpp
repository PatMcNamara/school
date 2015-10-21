/* Patrick McNamara */
/* cs4410 HW 2 */
/* 10/19/2015 */
#include<windows.h>
#include<gl/GL.h>
#include<gl/glut.h>

const int SLICES = 15;
const int STACKS = 15;

struct GLfloatPoint {
	GLfloat x, y, z;
};

GLfloatPoint center;
float radius;

void moveSphere() {

}

/*void drawSphere() {
	glutSolidSphere(radius, SLICES, STACKS);
}*/