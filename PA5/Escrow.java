public class Escrow{

    //This should be static
    //Transaction completes or an exception occurs
    //Player refecence Es, provides funds and indicates item

    //calls store method from try catch
    //store provides or throws exception
    //player deducts funds or catch

    //intermediated
    //gets money from player, is passed to store, and then store gives item and get money
    //back to play passes item

    //Commands

    private static double amountInEscrow;
    public static Item requestItem;
    public static Item itemInEscrow;

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