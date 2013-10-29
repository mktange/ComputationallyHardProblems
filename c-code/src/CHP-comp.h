#define max(a,b) \
   ({ __typeof__ (a) _a = (a); \
       __typeof__ (b) _b = (b); \
     _a > _b ? _a : _b; })

#ifndef CHP
#define CHP

// Structs
typedef struct Edge {
	int id, n1, n2, w;
} Edge;

typedef struct EdgeList {
	Edge *e;
	struct EdgeList * next;
} Item;

typedef struct Solution {
	int B;
	char * edges;
} Solution;


// Functions
int main(int argc, char ** argv);

char* getFilename(int argc, char ** argv);
int readGraph(char * filename);

void recursiveSolve(int k, int st, int mot);

int getOther(int node, Edge * e);

int hasLoop(int k);
int cycleDFS(int k, int from, int node);

int isConnected(int k);
void connectedDFS(int k, int node);

// Print functions
void printEdge(Edge * e);
void printGraph();
void printSolution();

#endif
