public class Program {
    public static void main(String[] args) throws Exception {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GraphForm();
            }
        });
    }
}