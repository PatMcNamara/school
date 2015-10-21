class Edge {
    int neighbor;
    int cost;
    Edge next;

    Edge(int neighbor_, Edge next_) {
	neighbor = neighbor_;
	next = next_;
	cost = 1;
    }

    Edge(int neighbor_, Edge next_, int cost_) {
	neighbor = neighbor_;
	next = next_;
	cost = cost_;
    }
}