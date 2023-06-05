
public class IndividuoFactory {
	public static Individuo<?> getIndivType(int n, double valError, int d) {
		switch(n) {
		case 1:  return new IndividuoFuncion1(valError);
		case 2:	 return new IndividuoFuncion2(valError);
		case 3:	 return new IndividuoFuncion3(valError);
		case 4:  return new IndividuoFuncion4(valError, d);
		case 5:  return new IndividuoFuncion5(valError, d);
		default: return new IndividuoFuncion1(valError);
		}
	}
}
