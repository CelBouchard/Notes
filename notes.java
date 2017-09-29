// PROJECT 002, Matrixes

public Vector(Vector v) { }
  // Creates a new vector, and we want it to be a copy of the local vector we just made. 

public void scalarAddInPlace(double d) { }
  // Adds value d to all elements in vector.

public Vector scalarAdd(double d) { 
  // TODO, insert the following:
  // Vector.set = new Vector(this), set.scalarAddInPlace(d), return set;
}

// ----------------------------------------------------------

package linalg;

/*** A class that represents a multidimensional real-valued (double) vector 
* and supports various vector computations required in linear algebra. */

public class Vector {

	private int _nDim;       // Dimension of the Vector; nomenclature: _ for data member, n for integer
	private double[] _adVal; // Contents of the Vector; nomenclature: _ for data member, a for array, d for double

	/** Constructor: allocates space for a new vector of dimension dim
	 * @param dim
	 * @throws LinAlgException if vector dimension is < 1
	 */
	public Vector(int dim) throws LinAlgException { //Constructor needs 1 integer passed, assigns it name dim.
		if (dim <= 0)
			throw new LinAlgException("Vector dimension " + dim + " cannot be less than 1");
    
		_nDim = dim; // Has member var _nDim point to given integer dim.
		_adVal = new double[dim]; // Member var _adVal is now an array of doubles with dim spaces. 
                              // Entries will be automatically initialized to 0.0
	}
	
	/** Copy constructor: makes a new copy of an existing Vector v
	 *                    (note: this explicitly allocates new memory and copies over content)
	 * @param v
	 */
	public Vector(Vector v) { //has the same name as above construcotr, but compiler will know which to use depedngin on whether vector or int is passed
		_nDim = v._nDim; // Unchanged. v now has _nDim value passed into it, creating a vector of that size.
		_adVal = new double[_nDim]; // _adval represents array of dimension _nDim
		for (int index = 0; index < _nDim; index++) // index starts at 0, continues incrementing until it reaches the value of _nDim-1.
			_adVal[index] = v._adVal[index]; // sets values in v as according values in array _adval
	}
  
  // basically the above just created a vector v with space Dim ready to have values inserted into it.

	/** Constructor: creates a new Vector with dimension and values given by the string init
	 * 
	 * @param init: a String formatted like "[ -1.2 2.0 3.1 5.8 ]" (must start with [ and end with ])
	 * @throws LinAlgException if init is not properly formatted (missing [ or ], or improperly formatted number)
	 */
	public Vector(String init) throws LinAlgException {
		
		// The following says split init on whitespace (\\s) into an array of Strings
		String[] split = init.split("\\s");  
		 // for (int i = 0; i < split.length; i++)       // will print out shit like 0. [, 1. -1.2, etc.
		 	//	System.out.println(i + ". " + split[i]);

		if (!split[0].equals("[") || !split[split.length-1].equals("]"))
			throw new LinAlgException("Malformed vector initialization: missing [ or ] in " + init); // throws exception if there's no  brackets

		// We don't count the [ and ] in the dimensionality
		_nDim = split.length - 2; // sets _nDim as middle values of given init / new split array of strings
		_adVal = new double[_nDim]; // creates array on proper vector dimensions
		
		// Parse each number from init and add it to the Vector in order (note the +1 offset to account for [)
		for (int index = 0; index < _nDim; index++) {
			try {
				set(index, Double.parseDouble(split[index + 1])); // sets values of appropriate split string to vector v (set tbd ig?)
			} catch (NumberFormatException e) { // throws linalg exception if the given string isn't a double
				throw new LinAlgException("Malformed vector initialization: could not parse " + split[index + 1] + " in " + init);
			}
		}
	}

	/** Overrides method toString() on Object: converts the class to a human readable String
	 * 
	 *  Note 1: this is invoked *automatically* when the object is listed where a String is expected,
	 *          e.g., "System.out.println(v);" is actually equivalent to "System.out.println(v.toString());"       
	 *          
	 *  Note 2: for debugging purposes, you should always define a toString() method on a class you define
	 */
	@Override // optional annotation to tell Java we expect this overrides a parent method -- compiler will warn if not
	public String toString() {
		// We could just repeatedly append to an existing String, but that copies the String each
		// time, whereas a StringBuilder simply appends new characters to the end of the String
		StringBuilder sb = new StringBuilder();
		sb.append("["); //replaces bracket with bracket
		for (int i = 0; i < _nDim; i++)
			sb.append(String.format(" %6.3f ", _adVal[i])); // Append each vector value in order, now with 6 spaces and 3 sigfigs per float
		sb.append(" ]"); //replaces bracket with bracket
		return sb.toString();
	}

	/** Overrides address equality check on Object: allows semantic equality testing of vectors,
	 *  i.e., here we say two objects are equal if and only if they have the same dimensions and values
	 *        match at all indices
	 * 
	 * Note: you should almost always define equals() since the default equals() on Object simply
	 *       tests that two objects occupy the same space in memory (are actually the same instance), 
	 *       but does not test that two objects may be different instances but have the same content
	 *       
	 * @param o the object to compare to
	 */
	@Override // optional annotation to tell Java we expect this overrides a parent method -- compiler will warn if not
	public boolean equals(Object o) {
		if (o instanceof Vector) { //if o is an instance of Vector class
			Vector v = (Vector)o; // This is called a cast (or downcast)... we can do it since we
			                      // know from the if statement that o is actually of subtype Vector
			if (_nDim != v._nDim)
				return false; // Two Vectors cannot be equal if they don't have the same dimension
			for (int index = 0; index < _nDim; index++)
				if (_adVal[index] != v._adVal[index])
					return false; // If two Vectors mismatch at any index, they are not equal
			return true; // Everything matched... objects are equal!
		} else // if we get here "(o instanceof Vector)" was false
			return false; // Two objects cannot be equal if they don't have the same class type
	}
	
	/** Get the dimension of this vector
	 * 
	 * @return: the dimensionality of this Vector
	 */
	public int getDim() {
		return _nDim; // or v._nDim?
	}

	/** Returns the value of this vector at the given index (remember: array indices start at 0)
	 * 
	 * @param index
	 * @return
	 * @throws LinAlgException if array index is out of bounds (see throw examples above)
	 */
	public double get(int index) throws LinAlgException {
		return _adVal[index + 1]; // or v._adVal[]? + 1 to account for 0, bracket already accounted for above
	}

	/** Set the value val of the vector at the given index (remember: array indices start at 0)
	 * 
	 * @param index
	 * @param val
	 * @throws LinAlgException if array index is out of bounds (see throw examples above)
	 */
	public void set(int index, double val) throws LinAlgException {
		// TODO. sets values of splitted string into vector v
    v._adVal[index] = "val";
	}
	
	/** Change the dimension of this Vector by *reallocating array storage* and copying content over
	 *  ... if new dim is larger than current dim then the additional indices take value 0.0
	 *  ... if new dim is smaller than current dim then any indices in current vector beyond current
	 *      dim are simply lost
	 * 
	 * @param new_dim
	 * @throws LinAlgException if vector dimension is < 1
	 */
	public void changeDim(int new_dim) {
		//todo. lul watamidoin
    if( new_dim > dim ) {
      new_adVal = new double[new_dim];
      for (int index=0; index < dim; index++)
        new_adVal[index] = _adVal[index];
    }
    else if( new_dim < 1 ) {
      throw new LingAlgException("Vector dimension cannot be less than 1!");
    } 
    else if( new_dim < dim ) {
      new_adVal = new double[new_dim];
      for (int index=0; index < new_dim; index++)
        new_adVal[index] = _adVal[index];
    } else { return _adval; }
	}
	
	/** This adds a scalar d to all elements of *this* Vector
	 *  (should modify *this*)
	 * 
	 * @param d
	 */
	public void scalarAddInPlace(double d) {
		for (int index = 0; index < _nDim; index++)
			_adVal[index] += d;
	}
	
	/** This creates a new Vector, adds a scalar d to it, and returns it
	 *  (should not modify *this*)
	 * 
	 * @param d
	 * @return new Vector after scalar addition
	 */
	public Vector scalarAdd(double d) {
		_nDim = v._nDim; 
		new_adVal = new double[_nDim]; 
		for (int index = 0; index < _nDim; index++) 
			new_adVal[index] += d; 
    return new_adVal;
	}
	
	/** This multiplies a scalar d by all elements of *this* Vector
	 *  (should modify *this*)
	 * 
	 * @param d
	 */
	public void scalarMultInPlace(double d) {
		for (int index = 0; index < _nDim; index++)
			_adVal[index] *= d;
	}
	
	/** This creates a new Vector, multiplies it by a scalar d, and returns it
	 *  (should not modify *this*)
	 * 
	 * @param d
	 * @return new Vector after scalar addition
	 */
	public Vector scalarMult(double d) {
		_nDim = v._nDim; 
		new_adVal = new double[_nDim]; 
		for (int index = 0; index < _nDim; index++) 
			new_adVal[index] *= d; 
    return new_adVal;
	}

	/** Performs an elementwise addition of v to *this*, modifies *this*
	 * 
	 * @param v
	 * @throws LinAlgException if dimensions of the two operand vectors do not match
	 */
	public void elementwiseAddInPlace(Vector v) throws LinAlgException {
		for (int index = 0; index < _nDim; index++)
			v._adVal[index] += _adVal[index];
	}

	/** Performs an elementwise addition of *this* and v and returns a new Vector with result
	 * 
	 * @param v
	 * @return
	 * @throws LinAlgException if dimensions of the two operand vectors do not match
	 */
	public Vector elementwiseAdd(Vector v) throws LinAlgException {
		_nDim = v._nDim; 
		new_adVal = new double[_nDim]; 
		for (int index = 0; index < _nDim; index++) 
			v._adVal[index] += _adVal[index];
    return new_adVal;
	}
	
	/** Performs an elementwise multiplication of v and *this*, modifies *this*
	 * 
	 * @param v
	 * @throws LinAlgException if dimensions of the two operand vectors do not match
	 */
	public void elementwiseMultInPlace(Vector v) throws LinAlgException {
		for (int index = 0; index < _nDim; index++)
			v._adVal[index] *= _adVal[index];
	}

	/** Performs an elementwise multiplication of *this* and v and returns a new Vector with result
	 * 
	 * @param v
	 * @return
	 * @throws LinAlgException if dimensions of the two operand vectors do not match
	 */
	public Vector elementwiseMult(Vector v) throws LinAlgException {
		_nDim = v._nDim; 
		new_adVal = new double[_nDim]; 
		for (int index = 0; index < _nDim; index++) 
			v._adVal[index] *= _adVal[index];
    return new_adVal;
	}

	/** Performs an inner product of Vectors v1 and v2 and returns the scalar result
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 * @throws LinAlgException
	 */
	public static double InnerProd(Vector v1, Vector v2) throws LinAlgException {
    double dProd = 0;
    
    for (int index = 0; index < _nDim; index++)
      dProd += (v1._adVal[index] *v2_adVal[index]);
  
		return dProd;
	}
}
