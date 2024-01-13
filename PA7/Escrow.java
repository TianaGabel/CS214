public class Escrow{

    private static double amountInEscrow;
    public static Item requestItem;
    public static Item itemInEscrow;

    private Escrow(){}

    public static void escrowItem(Item item){
        itemInEscrow = item;
    }

    public static void escrowMoney(double amount){
        amountInEscrow = amount;
    }

    public static void requestItem(Item item){
        requestItem = item;
    }




    //Queries
    public static Item receiveItem(){
        Item returnItem = itemInEscrow;
        itemInEscrow = null;
        return returnItem;
    }

    public static double receiveMoney(){
        double returnAmount = amountInEscrow;
        amountInEscrow = 0;
        return returnAmount;
    }

    public static double returnMoney(){
        double returnAmount = amountInEscrow;
        amountInEscrow = 0;
        return returnAmount;
    }

    public static Item returnItem(){
        Item returnItem = itemInEscrow;
        itemInEscrow = null;
        return returnItem;
    }

}