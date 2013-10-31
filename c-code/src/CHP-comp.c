#define DEBUG

#include "CHP-comp.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

int n, m;
Edge * edges;
Edge ** sorted;
Item * items;
Item ** nbs;

int bestB;
char * bestEdges;

char *contracted;
int c_count;

char *explored;
int explored_count;

int main(int argc, char ** argv) {
	char * filename = getFilename(argc, argv);

	if (readGraph(filename) == 0) {
		printf("Failed to read input file: %s\n", filename);
		fflush(stdout);
		return EXIT_FAILURE;
	}
	free(filename);

	// Initialize variables and arrays
	bestB = 2147483647;
	bestEdges = malloc(m*sizeof(char));
	c_count = 0;
	int i;

	explored = malloc(n * sizeof(char));
	contracted = malloc(m * sizeof(char));
	sorted = malloc(m * sizeof(Edge*));

	for (i = 0; i < m; ++i) {
		contracted[i] = 0;
		sorted[i] = &edges[i];
	}

	QuickSort(sorted, 0, m-1);
	for (i = 0; i < m; ++i) sorted[i]->pos = i;

	// Find solution
#ifdef DEBUG
	clock_t start, end;
	start = clock();
#endif
	recursiveSolve(0, 0, 0);
#ifdef DEBUG
	end = clock();
	printf(	"Time to find solution: %f\n",
			(double)(end-start) / CLOCKS_PER_SEC);
#endif

	// Print solution
	printSolution();

	// Free allocated memory
	free(contracted);
	//free(explored);
	free(edges);
	free(items);
	free(bestEdges);

	return EXIT_SUCCESS;
}

void recursiveSolve(int k, int st, int mot) {
	// Check whether our current weight is too great,
	// or if we have created a loop
	if (max(st,mot) >= bestB || hasLoop(k)) {
		return;
	}

	// Check if new best is found
	if (c_count >= n-1) {
		bestB = max(st,mot);
		memcpy(bestEdges, contracted, m*sizeof(char));
		return;
	}

	// Check if it is still connected
	if (!isConnected(k)) {
		return;
	}

	contracted[sorted[k]->id] = 1;
	c_count++;
	recursiveSolve(k+1, st+sorted[k]->w, mot+edges[m-1-sorted[k]->id].w);

	contracted[sorted[k]->id] = 0;
	c_count--;
	recursiveSolve(k+1, st, mot);
}

/**
 * Helper functions
 */

int hasLoop(int k) {
	if (c_count < 1) return 0;

	// Reset explored set
	int i;
	for (i = 0; i < m; ++i) explored[i] = 0;

	// Check if there is a cycle in the contracted set
	return cycleDFS(-1, sorted[k-1]->n1);
}

int cycleDFS(int from, int node) {
	explored[node] = 1;

	Item * item = nbs[node];
	while (item != NULL) {
		int other = getOther(node, item->e);
		if (other == from || contracted[item->e->id] == 0) {
			item = item->next;
			continue;
		}

		if (explored[other] == 1 || cycleDFS(node, other)) {
			return 1;
		}
		item = item->next;
	}

	return 0;
}

int isConnected(int k) {
	// Reset explored set
	int i;
	for (i = 0; i < m; ++i) explored[i] = 0;

	explored_count = 0;
	// Check if there is a cycle in the contracted set
	connectedDFS(k, 0);
	return explored_count == n;
}

void connectedDFS(int k, int node) {
	explored[node] = 1;
	explored_count++;

	Item * item = nbs[node];
	while (item != NULL) {
		int other = getOther(node, item->e);
		if (explored[other] == 1) {
			item = item->next;
			continue;
		}

		if (item->e->pos < k && contracted[item->e->id] == 0) {
			item = item->next;
			continue;
		}

		connectedDFS(k, other);
		item = item->next;
	}
}

int getOther(int node, Edge * e) {
	if (e == NULL) return -1;
	if (e->n1 == node) return e->n2;
	if (e->n2 == node) return e->n1;
	return -1;
}

/**
 * Initialization functions
 */

int readGraph(char * filename){
	FILE * fp;
	fp = fopen(filename, "r");
	if (fp == NULL)	{
		return 0;
	}

	fscanf(fp, "%d", &n);
	fscanf(fp, "%d", &m);

	edges = malloc(m * sizeof(Edge));
	nbs = malloc(n * sizeof(Item*));

	int n1, n2, w;
	items = malloc(2*m * sizeof(Item));
	int i = 0;
	int h;
	while (fscanf(fp, "%d %d %d", &n1, &n2, &w) == 3) {
		edges[i].id = i;
		edges[i].n1 = n1-1;
		edges[i].n2 = n2-1;
		edges[i].w = w;
		if (i >= m/2) {
			h = EDGE_HEURISTIC(edges, i, m-i-1);
			edges[i].h1 = h;
			edges[m-i-1].h1 = h;
		}

		items[i].e = &edges[i];
		items[i].next = nbs[n1-1];
		nbs[n1-1] = &items[i];

		items[i+m].e = &edges[i];
		items[i+m].next = nbs[n2-1];
		nbs[n2-1] = &items[i+m];

		i++;
	}

	fclose(fp);

	return 1;
}

char* getFilename(int argc, char ** argv) {
	char in[30];

	if (argc > 1) {
		strcpy(in, argv[1]);
	} else {
		printf("Enter filename: ");
		fflush(stdout);
		scanf("%s", in);
	}

	char * filename = malloc(120 * sizeof(char));
	strcpy(filename, "testfiles/");
	strcat(filename, in);
	strcat(filename, ".uwg");
	return filename;
}

/**
 * Sorting
 */

void QuickSort(QS_TYPE * list, int beg, int end) {
	QS_TYPE piv; QS_TYPE tmp;
	int l, r, p;

	while (beg < end) {
		l = beg;
		p = beg + (end-beg)/2;
		r = end;
		piv = list[p];

		while (1) {
			while ((l<=r) && (QS_COMPARE(list[l], piv) <= 0)) l++;
			while ((l<=r) && (QS_COMPARE(list[r], piv) > 0)) r--;
			if (l>r) break;

			tmp = list[l];
			list[l] = list[r];
			list[r] = tmp;

			if (p == r) p = l;
			l++;
			r--;
		}

		list[p] = list[r];
		list[r] = piv;
		r--;

		// Recursion on the shorter side & loop (with new indexes) on the longer
		if ((r-beg) < (end-l)) {
			QuickSort(list, beg, r);
			beg = l;
		} else {
			QuickSort(list, l, end);
			end = r;
		}
	}
}

/**
 * Useful printing functions
 */

void printGraph() {
	printf("n: %d\n", n);
	printf("m: %d\n", m);

	int i;
	for (i = 0; i < m; ++i) {
		printEdge(&edges[i]);
	}

	Item * item;
	for (i = 0; i < n; ++i) {
		item = nbs[i];
		printf("%d: ", i+1);

		while (item != NULL && item->next != NULL) {
			printf("%d, ", getOther(i, item->e)+1);
			item = item->next;
		}
		printf("%d\n", getOther(i, item->e)+1);
	}
}

void printEdge(Edge * e) {
	printf("e%d = (%d,%d), w = %d\n",
			e->id+1,
			e->n1+1,
			e->n2+1,
			e->w);
}

void printSolution() {
	printf("%d\n", bestB);
	int i;
	for (i = 0; i < m; ++i) {
		if (bestEdges[i]) printf("%d ", i+1);
	}
	printf("\n");
	fflush(stdout);
}
