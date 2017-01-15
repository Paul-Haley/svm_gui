package libsvm;
public class svm_problem implements java.io.Serializable
{
	public int l;
	public double[] y;
	public svm_node[][] x;
	/*
	where `l' is the number of training data, and `y' is an array containing
    their target values. (integers in classification, real numbers in
    regression) `x' is an array of pointers, each of which points to a sparse
    representation (array of svm_node) of one training vector. 

    For example, if we have the following training data:

    LABEL    ATTR1    ATTR2    ATTR3    ATTR4    ATTR5
    -----    -----    -----    -----    -----    -----
      1        0        0.1      0.2      0        0
      2        0        0.1      0.3     -1.2      0
      1        0.4      0        0        0        0
      2        0        0.1      0        1.4      0.5
      3       -0.1     -0.2      0.1      1.1      0.1

    then the components of svm_problem are:

    l = 5

    y -> 1 2 1 2 3

    x -> [ ] -> (2,0.1) (3,0.2) (-1,?)
         [ ] -> (2,0.1) (3,0.3) (4,-1.2) (-1,?)
         [ ] -> (1,0.4) (-1,?)
         [ ] -> (2,0.1) (4,1.4) (5,0.5) (-1,?)
         [ ] -> (1,-0.1) (2,-0.2) (3,0.1) (4,1.1) (5,0.1) (-1,?)
	 */
}
