class Main {
    public static void main(String[] args) {
        Arquivo arquivo;

        try{
            arquivo = new Arquivo("TP1/Tarefas.db");
            
            Menu menu = new Menu();
            menu.exibeMenu();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}