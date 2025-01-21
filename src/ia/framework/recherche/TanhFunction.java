package ia.framework.recherche;

public class TanhFunction implements TransferFunction
{
    @Override
    public double evaluate(double value)
    {
        return Math.tanh(value);
    }

    /**
     * @param value résultat de la fonction d'évaluation
     */
    @Override
    public double evaluateDer(double value)
    {
        return 1 - Math.pow(value, 2);
    }
}
