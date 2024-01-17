import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> strs = new ArrayList<>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            String result = "";
            for (String s:strs){
                result += (s+"\n");
            }
            return result;
        } else if (url.getPath().contains("/add")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")){
                strs.add(parameters[1]);
                return String.format("%s added to list!", parameters[1]);
            }
            else{
                return String.format("Incorrect query format.");
            }
        } else if (url.getPath().contains("/search")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")){
                String result = "";
                for (String s:strs){
                    if (s.indexOf(parameters[1])>=0){
                        result += (s+"\n");
                    }
                }
                return result;
            }
            else{
                return String.format("Incorrect query format.");
            }
        }
        else {
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
