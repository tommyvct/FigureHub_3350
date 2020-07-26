package comp3350.pbbs.objects.Cards;

/**
 * The whole point of this interface is to facilitate and simplify persistence layer operations.
 * Cards are gonna share the same table.
 */
public interface ICard
{
    String REGEX = "^[a-zA-Z \\-.']*$";    // the format of a name

    String toStringShort();
    String getCardNum();
    String getCardName();
    String getHolderName();
}
