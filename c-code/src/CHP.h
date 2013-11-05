#define max(a,b) \
   ({ __typeof__ (a) _a = (a); \
       __typeof__ (b) _b = (b); \
     _a > _b ? _a : _b; })

#define min(a,b) \
   ({ __typeof__ (a) _a = (a); \
       __typeof__ (b) _b = (b); \
     _a > _b ? _b : _a; })

#define QS_TYPE Edge*
#define QS_COMPARE(a,b) (a->h1 - b->h1)

#if E == 1
#define EDGE_HEURISTIC(edges, i, mi) (edges[i].w + edges[mi].w)
#elif E == 2
#define EDGE_HEURISTIC(edges, i, mi) (min(edges[i].w, edges[mi].w))
#elif E == 3
#define EDGE_HEURISTIC(edges, i, mi) (i)
#else
#define EDGE_HEURISTIC(edges, i, mi) (max(edges[i].w, edges[mi].w))
#endif

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

int connectedDFSmatrix(int k, int node, int end);

#ifdef UF
short UFroot(int node);
#endif

// Sort
void QuickSort(QS_TYPE * list, int beg, int end);

// Print functions
void printEdge(Edge * e);
void printGraph();
void printSolution();

#endif
