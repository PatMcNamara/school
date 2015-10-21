/* Patrick McNamara */
/* cs4410 HW 2 */
/* 10/19/2015 */
#define _USE_MATH_DEFINES
#include <math.h>
#include <time.h> // Used for random seed.

#include <windows.h>
#include <gl/GL.h>
#include <gl/GLU.h>
#include <gl/glut.h>

struct GLfloatPoint {
	GLfloat x, y, z;
};

struct sphere {
	GLfloat material[4];
	struct GLfloatPoint center;
	int radius;
};

const int SCREEN_WIDTH = 640;
const int SCREEN_HEIGHT = 480;
const float VIEW_ANGLE = 60.0;
const float NEAR_PLANE = 10.0;
const float FAR_PLANE = 200.0;
const int SPHERE_COLOR_DEPTH = 100;

const int NUM_SPHERES = 8; // Number of spheres to draw.
const float SPHERE_RADIUS = 3;

// How much z varies between each call to idle func, must be < 1.
const float ZOOM_FACTOR = .05; // The higher this is, the fatster it zooms.

struct sphere spheres[NUM_SPHERES];

// Gives a random point inside the view volume at center.z
void getRandPointInViewVolume(GLfloatPoint *center, float radius) {
	float maxLocation = atan((VIEW_ANGLE / 2) * M_PI / 180) * center->z * 2;
	float maxLocationX = maxLocation * (float)SCREEN_WIDTH / SCREEN_HEIGHT;
	float maxLocationY = maxLocation * (float)SCREEN_HEIGHT / SCREEN_WIDTH;
	center->x = (rand() % (int)maxLocationX) - (maxLocationX / 2);
	center->y = (rand() % (int)maxLocationY) - (maxLocationY / 2);
}

// Init the openGL system.
void init() {
	glClearColor(1.0, 1.0, 1.0, 1.0);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluPerspective(VIEW_ANGLE, (float) SCREEN_WIDTH / SCREEN_HEIGHT, NEAR_PLANE, FAR_PLANE);

	// Set up camera
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	gluLookAt(0, 0, -NEAR_PLANE, 0, 0, 1, 0, 1, 0);

	// Set up lighting
	GLfloat lightPosition[] = { 40.0, 40.0, 0.0, 1.0 };
	glLightfv(GL_LIGHT0, GL_POSITION, lightPosition);
	glEnable(GL_LIGHTING);
	glEnable(GL_LIGHT0);

	glEnable(GL_CULL_FACE);
	glEnable(GL_DEPTH_TEST);

	// Create random spheres
	srand(time(NULL));
	for (int i = 0; i < NUM_SPHERES; i++) {
		spheres[i].center.z = (rand() % (int)FAR_PLANE) + NEAR_PLANE;
		spheres[i].radius = SPHERE_RADIUS;

		// Generate starting center point.
		getRandPointInViewVolume(&spheres[i].center, spheres[i].radius);

		// Generate sphere color.
		for (int j = 0; j < 3; j++) {
			spheres[i].material[j] = (float) (rand() % SPHERE_COLOR_DEPTH) / SPHERE_COLOR_DEPTH;
		}
		spheres[i].material[3] = 1.0;
	}
}

const int SLICES = 40;
const int STACKS = 40;

// Draws all of the spheres.
void drawSpheres() {
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	for (int i = 0; i < NUM_SPHERES; i++) {
		glPushMatrix();
		glMaterialfv(GL_FRONT, GL_DIFFUSE, spheres[i].material);
		glTranslated(spheres[i].center.x, spheres[i].center.y, spheres[i].center.z);
		glutSolidSphere(spheres[i].radius, SLICES, STACKS);
		glPopMatrix();
	}
	glFlush();
	glutSwapBuffers();
}

// Update each sphere's location
void updateSphereLocation() {
	for (int i = 0; i < NUM_SPHERES; i++) {
		// Move each sphere closer to Z.
		float newZ = spheres[i].center.z - ZOOM_FACTOR;

		// Edge of the sphere has reached the near plane. Go back to the far plane.
		if ((newZ - spheres[i].radius) <= 0) {
			// Get a random point on the far plane.
			spheres[i].center.z = FAR_PLANE - NEAR_PLANE - spheres[i].radius;
			getRandPointInViewVolume(&spheres[i].center, spheres[i].radius);
		} else {// Since the center of the near plane is at the origin, just move x and y by ratio of the z change.
			spheres[i].center = { newZ * (spheres[i].center.x / spheres[i].center.z), 
			                      newZ * (spheres[i].center.y / spheres[i].center.z),
								  newZ };
		}
	}
	glutPostRedisplay();
}

void keypress(unsigned char key, int x, int y) {
	if (key == 'q') {
		exit(0);
	}
}

int main(int argc, char **argv) {
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);
	glutInitWindowSize(SCREEN_WIDTH, SCREEN_HEIGHT);
	glutInitWindowPosition(100, 150);
	glutCreateWindow("Flying Spheres");
	glutDisplayFunc(drawSpheres);
	glutKeyboardFunc(keypress);
	glutIdleFunc(updateSphereLocation);
	init();
	glutMainLoop();
}

