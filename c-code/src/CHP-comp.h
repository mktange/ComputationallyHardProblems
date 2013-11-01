#define max(a,b) \
   ({ __typeof__ (a) _a = (a); \
       __typeof__ (b) _b = (b); \
     _a > _b ? _a : _b; })

#define avg(a,b) abs((a-b)/2)

#define QS_TYPE Edge*
#define QS_COMPARE(a,b) (a->h1 - b->h1)
//#define EDGE_HEURISTIC(edges, i, mi) (edges[i].w + edges[mi].w)
#define EDGE_HEURISTIC(edges, i, mi) (max(edges[i].w, edges[mi].w))
//#define EDGE_HEURISTIC(edges, i, mi) (avg(edges[i].w, edges[mi].w))
//#define EDGE_HEURISTIC(edges, i, mi) (i)

#ifndef CHP
#define CHP

// Structs
typedef struct Edge {
	int id, n1, n2;
	int w, h1;
	int pos;
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

int hasCycle(int k);
int cycleDFS(int from, int node);

int isConnected(int k);

#ifdef MATRIX
int connectedDFSmatrix(int k, int node, int end);
#else
void connectedDFS(int k, int node);
#endif

// Sort
void QuickSort(QS_TYPE * list, int beg, int end);

// Print functions
void printEdge(Edge * e);
void printGraph();
void printSolution();

#endif
