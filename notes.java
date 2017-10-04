package linalg;

/*** A class that represents a two dimensional real-valued (double) matrix
 *   and supports various matrix computations required in linear algebra.
 *   
 *   Class and method comments are in JavaDoc: https://en.wikipedia.org/wiki/Javadoc
 * 
 * @author ssanner@mie.utoronto.ca, celeste.bouchard@mail.utoronto.ca
 *
 */
public class Matrix {

	private int _nRows; // Number of rows in this matrix; nomenclature: _ for data member, n for integer
	private int _nCols; // Number of columns in this matrix; nomenclature: _ for data member, n for integer
	private double[][] _adMat;
	// represents matrix content, used 2D array. Other option is array of Vectors (e.g., for each row)
	
	/** Allocates a new matrix of the given row and column dimensions
	 * 
	 * @param rows
	 * @param cols
	 * @throws LinAlgException if either rows or cols is <= 0
	 */
	public Matrix(int rows, int cols) throws LinAlgException {
		if (rows <= 0 || cols <= 0 )
			throw new LinAlgException("Matrix dimension cannot be less than 1");
                
		_nRows = rows;
                _nCols = cols;
		_adMat = new double[rows][cols]; // Entries will be automatically initialized to 0.0
	}
	
	/** Copy constructor: makes a new copy of an existing Matrix m
	 *                    (note: this explicitly allocates new memory and copies over content)
	 * 
	 * @param m
	 */
	public Matrix(Matrix m) {
		_nRows = m._nRows; //sets dimensions appropriately.
		_nCols = m._nCols;
		_adMat = new double[_nRows][_nCols];
		
                for (int i = 0; i < _nRows; i++) {
                    for (int j = 0; j < _nCols; j++)
			_adMat[i][j] = m._adMat[i][j]; //copying values as appropriate
                }
	}

	/** Constructs a String representation of this Matrix
	 * 
	 */
        @Override // optional annotation to tell Java we expect this overrides a parent method -- compiler will warn if not
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int j = 0; j < _nCols; j++) {
                sb.append("[");
                    for (int i = 0; i < _nRows; i++)
			sb.append(String.format(" %6.3f ", _adMat[i][j])); // Append each vector value in order
		sb.append(" ] \n"); // s.t. the matrix is a "box" as apposed to one long line
                }
		return sb.toString();
	}

	/** Tests whether another Object o (most often a matrix) is a equal to *this*
	 *  (i.e., are the dimensions the same and all elements equal each other?)
	 * 
	 * @param o the object to compare to
	 */
        @Override
	public boolean equals(Object o) {
            
		if (o instanceof Matrix) {
			Matrix m = (Matrix)o; // This is called a cast (or downcast)... we can do it since we
			                      // know from the if statement that o is actually of subtype Vector
			
                        if ((_nRows != m._nRows) || (_nCols != m._nCols))
				return false; // Two Matrices cannot be equal if they don't have the same dimension
			
                        for (int i = 0; i < _nRows; i++) {
                            for (int j = 0; j < _nRows; j++) {
				if (_adMat[i][j] != m._adMat[i][j])
					return false; // If two Matrices mismatch at any index, they are not equal
                            }
                        } return true; // Everything matched... objects are equal!
		} else // if we get here "(o instanceof Vector)" was false
			return false; // Two objects cannot be equal if they don't have the same class type
	}
	
	/** Return the number of rows in this matrix
	 *   
	 * @return 
	 */
	public int getNumRows() {
		return _nRows;
	}

	/** Return the number of columns in this matrix
	 *   
	 * @return 
	 */
	public int getNumCols() {
		return _nCols;
	}

	/** Return the scalar value at the given row and column of the matrix
	 * 
	 * @param row
	 * @param col
	 * @return
	 * @throws LinAlgException if row or col indices are out of bounds
	 */
	public double get(int row, int col) throws LinAlgException {
		if(row < 0 || row >= _nRows || col < 0 || col >= _nCols)
                    throw new LinAlgException("Invalid Index."); 
		
                return _adMat[row][col]; 
	}
	
	/** Return the Vector of numbers corresponding to the provided row index
	 * 
	 * @param row
	 * @return
	 * @throws LinAlgException if row is out of bounds
	 */
	public Vector getRow(int row) throws LinAlgException {
		
            Vector new_adVec = new Vector(_nCols); // s.t. the row of lenth nCols is displayed
            
            if(row < 0 || row > _nRows) // out of bounds if given int is not in number of rows
                    throw new LinAlgException("Invalid Index."); 
		
            for(int i = 0; i < _nCols; i++)
                    new_adVec.set(i, row);
                
            return new_adVec;
        }

	/** Set the row and col of this matrix to the provided val
	 * 
	 * @param row
	 * @param col
	 * @param val
	 * @throws LinAlgException if row or col indices are out of bounds
	 */
	public void set(int row, int col, double val) throws LinAlgException {
		if(row < 0 || row >= _nRows || col < 0 || col >= _nCols)
                    throw new LinAlgException("Invalid Dimensions.");
            
            _adMat[col][row] = val; // these are reversed WHY
	}
	
	/** Return a new Matrix that is the transpose of *this*, i.e., if "transpose"
	 *  is the transpose of Matrix m then for all row, col: transpose[row,col] = m[col,row]
	 *  (should not modify *this*)
	 * 
	 * @return
	 * @throws LinAlgException
	 */
	public Matrix transpose() throws LinAlgException {
		Matrix transpose = new Matrix(_nCols, _nRows); // creates new matrix of original's size
		for (int row = 0; row < _nRows; row++) {
			for (int col = 0; col < _nCols; col++) {
				transpose.set(col, row, get(row,col)); //sets values of transpose as opposite to orig.
			}
		}
		return transpose;
	}

	/** Return a new Matrix that is the square identity matrix (1's on diagonal, 0's elsewhere) 
	 *  with the number of rows, cols given by size.  E.g., if size = 3 then the returned matrix
	 *  would be the following:
	 *  
	 *  [ 1 0 0 ]
	 *  [ 0 1 0 ]
	 *  [ 0 0 1 ]
	 * 
	 * @param size
	 * @return
	 * @throws LinAlgException if the size is <= 0
	 */
	public static Matrix GetIdentity(int size) throws LinAlgException {
            if(size <= 0)
                throw new LinAlgException("Invalid Dimensions");
            
            Matrix new_mat = new Matrix(size,size); // new matrix
            
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if(i==j) { // on the diagonal
                        new_mat.set(i, j, 1);
                    } else { // not on diagonal
                        new_mat.set(i, j, 0); 
                    }
		}
            }
            
            return new_mat;
	}
	
	/** Returns the Matrix result of multiplying Matrix m1 and m2
	 *  (look up the definition of matrix multiply if you don't remember it)
	 * 
	 * @param m1
	 * @param m2
	 * @return
	 * @throws LinAlgException if m1 columns do not match the size of m2 rows
	 */
	public static Matrix Multiply(Matrix m1, Matrix m2) throws LinAlgException {
		if(m1.getNumCols() != m2.getNumRows())
                        throw new LinAlgException("Invalid Dimensions.");
                
                double prod; // value of dot product
                Matrix mult_mat = new Matrix(m1.getNumRows(),m2.getNumCols()); // multiplied matrix will
                                                                               // have the size of rows from 
                                                                               // 1 and cols from 2
                Matrix m2_trans = m2.transpose(); // m2 transposed to evaluate the dot prod. more easily
                
                for(int i = 0; i < (m1.getNumRows()); i++) { // loop wil switch rows after going through all of its vals
                    for(int j = 0; j < (m2.getNumCols()); j++) { // going through all of the vals in one row
                        Vector vec_1 = m1.getRow(i); // m1 row 
                        Vector vec_2 = m2_trans.getRow(j); // m2_trans row
                        prod = Vector.InnerProd(vec_1,vec_2); // dot product of both
                        mult_mat.set(i,j,prod); // set it in place for mult_mat matrix
                    }
                }
                
		return mult_mat;
	}
		
	/** Returns the Vector result of multiplying Matrix m by Vector v (assuming v is a column vector)
	 * 
	 * @param m
	 * @param v
	 * @return
	 * @throws LinAlgException if m columns do match the size of v
	 */
	public static Vector Multiply(Matrix m, Vector v) throws LinAlgException {
		if (m.getNumCols() != v.getDim())
                    throw new LinAlgException("Invalid Dimensions.");
                
                double prod;
                Vector mult_adVec = new Vector(m.getNumRows());
                
                for(int i = 0; i < (m.getNumRows()); i++) {
                        prod = 0;
                        for(int j = 0; j < (v.getDim()); j++) {
                            prod += (m.get(i,j)*v.get(j));
                        }
                        mult_adVec.set(i,prod);
                }
                
		return mult_adVec;
	}

}

