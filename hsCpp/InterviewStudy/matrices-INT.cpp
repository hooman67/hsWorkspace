#include "hsGlobolHeader.h"
/*temp = top; left->top; bottom->left; right->botom; temp -> right
top = [first][i]
bottome = [last][last-offset]
right = [i][last]
left = [last-offset][first]
*/
void rotate90(int** a, int n, int** matrix) { //O(n^2) mem: O(1)         call:  rotate90<int[3][3]>(a,3)
	for (int layer = 0; layer < n / 2; layer++) {
		int first = layer;
		int last = n - 1 - layer;
		for (int i = first; i < last; i++){
			int offset = i - first;		//u want offset to be positive i grows while first stays the same 	
			int top = matrix[first][i]; // save top			
			matrix[first][i] = matrix[last - offset][first];// left -> top			
			matrix[last - offset][first] = matrix[last][last - offset]; // bottom -> left
			matrix[last][last - offset] = matrix[i][last]; // right -> bottom
			matrix[i][last] = top; // top -> right
		}
	}
}


//find the largest square (with borders = black) in a matrix of white and black cells.

//method 1: less efficient. O(n^4)
class square{ //this class can represent a sub square within a matrix
public:
	int row, col, size;
	square(int r = 0, int c = 0, int s = 0) :row(r), col(c), size(s){}
	square(const square& s){
		row = s.row;
		col = s.col;
		size = s.size;
	}
	void print(int** matrix){
		for (int i = row; i < (size + row); i++){
			for (int j = col; j < (size + col); j++)
				cout << matrix[i][j] << "  ";
			cout << "\n";
		}
	}
};
square findSquare(int**matrix, int s) {  //O(n^4)  where n = s = length of one side of the sqr matrix (find sqr with size is O(N^3))
	square sqr;
	for (int i = s; i >= 1; i--) {
		sqr = findSquareWithSize(matrix, s, i);
		if (sqr.size != 0)
			break;
	}
	return sqr;
}
bool isSquare(int** matrix, int row, int col, int size){ //O(N) whre N = size
	// Check top and bottom border.
	for (int j = 0; j < size; j++){
		if (matrix[row][col + j] == 1)
			return false;
		if (matrix[row + size - 1][col + j] == 1)
			return false;
	}
	// Check left and right border.
	for (int i = 1; i < size - 1; i++){
		if (matrix[row + i][col] == 1)
			return false;
		if (matrix[row + i][col + size - 1] == 1)
			return false;
	}
	return true;
}
square findSquareWithSize(int** matrix, int s, int squareSize) {//(N^3) because isSquare is O(N)
	// On an edge of length N, there are (N - sz + 1) squares of length sz.
	int count = s - squareSize + 1;
	//Iterate through all squares with side length squareSize. 
	for (int row = 0; row < count; row++){
		for (int col = 0; col < count; col++){
			if (isSquare(matrix, row, col, squareSize)){
				return square(row, col, squareSize);
			}
		}
	}
	return square();
}

//method 2: more efficient. O(n^3)
square findSquare(int** matrix, int s){//O(n^3)   where n is the length of 1 side of the sqr matrix
	pair<int, int>** processed = processSquare(matrix, s);
	square sqr;
	for (int i = s; i >= 1; i--){
		sqr = findSquareWithSize(processed, s, i);
		if (sqr.size != 0)
			break;
	}
	return sqr;
}
//pair.first = # of zeroes to Right. (including self).
//pair.second = # of zeroes to bottom. (including self)
pair<int, int>** processSquare(int** matrix, int s){ //O(n^2)  == O(size of matrix)
	pair<int, int>** processed = new pair<int, int>*[s];
	for (int i = 0; i < s; i++)
		processed[i] = new pair<int, int>[s];
	for (int r = s - 1; r >= 0; r--){
		for (int c = s - 1; c >= 0; c--){
			processed[r][c] = make_pair(0, 0);
			// only need to process if it's a black cell
			if (matrix[r][c] == 0){
				processed[r][c].first++;
				processed[r][c].second++;
				// next column over is on same row
				if (c + 1 < s)
					processed[r][c].first += processed[r][c + 1].first;
				if (r + 1 < s)
					processed[r][c].second += processed[r + 1][c].second;
			}
		}
	}
	return processed;
}
bool isSquare(pair<int, int>** proc, int row, int col, int size){ //O(1)
	pair<int, int> topLeft = proc[row][col];
	pair<int, int> topRight = proc[row][col + size - 1];
	pair<int, int> bottomLeft = proc[row + size - 1][col];
	if (topLeft.first < size)// Check top edge
		return false;
	if (topLeft.second < size) // Check left edge
		return false;
	if (topRight.second < size)  // Check right edge
		return false;
	if (bottomLeft.first < size)  // Check bottom edge
		return false;
	return true;
}
square findSquareWithSize(pair<int, int>** proc, int s, int squareSize) {//(N^2) because isSquare is O(1)
	// On an edge of length N, there are (N - sz + 1) squares of length sz.
	int count = s - squareSize + 1;
	//Iterate through all squares with side length squareSize. 
	for (int row = 0; row < count; row++){
		for (int col = 0; col < count; col++){
			if (isSquare(proc, row, col, squareSize))
				return square(row, col, squareSize);
		}
	}
	return square();
}

//This is the most efficient method O(n^3)   or  O(rows^2*cols). Retuns the sub matrix with maximum sum of all cells
class SubMat{ //this class represents a sub matrix within a matrix (tracks only the coordinates)
public:
	int rowS, colS, rowE, colE;
	SubMat(int rowStart = 0, int rowEnd = 0, int colStart = 0, int colEnd = 0) :rowS(rowStart), rowE(rowEnd), colS(colStart), colE(colEnd){}
	SubMat(const SubMat& s){
		rowS = s.rowS;
		colS = s.colS;
		rowE = s.rowE;
		colE = s.colE;
	}
	void print(int** matrix){
		for (int i = rowS; i <= rowE; i++){
			for (int j = colS; j <= colE; j++)
				cout << matrix[i][j] << "  ";
			cout << "\n";
		}
	}
};
void clearArray(int* array, int s){
	for (int i = 0; i <s; i++)
		array[i] = 0;
}
pair<pair<int, int>, int> maxSubArray(int* array, int s){
	int maxSum = 0;
	int runningSum = 0;
	int colE = 0;
	int colS = 0;
	for (int i = 0; i < s; i++){
		runningSum += array[i];
		if (runningSum > maxSum){
			maxSum = runningSum;
			colE = i;
		}
		/* If runningSum is < 0, no point in trying to continue the * series. Reset. */
		if (runningSum < 0){
			runningSum = 0;
			colS = i + 1;
		}
	}
	return make_pair(make_pair(colS, colE), maxSum);
}
SubMat maxSubMatrix(int** matrix, int rowCount, int colCount){
	int* partialSum = new int[colCount];
	int maxSum = 0; // Max sum is an empty matrix
	SubMat sqr;
	for (int rowStart = 0; rowStart < rowCount; rowStart++){
		clearArray(partialSum, colCount);
		for (int rowEnd = rowStart; rowEnd < rowCount; rowEnd++){
			for (int i = 0; i < colCount; i++)
				partialSum[i] += matrix[rowEnd][i];
			auto temp = maxSubArray(partialSum, colCount);
			/* If you want to track the coordinates, add code here to do that. */
			if (temp.second > maxSum){
				maxSum = temp.second;
				sqr.colS = temp.first.first;
				sqr.colE = temp.first.second;
				sqr.rowS = rowStart;
				sqr.rowE = rowEnd;
			}
		}
	}
	return sqr;
}
