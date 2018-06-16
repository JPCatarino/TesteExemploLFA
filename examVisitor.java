import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class examVisitor extends examGrmrBaseVisitor<String>{
    private Map<String,String> incogni = new HashMap<String,String>();


    @Override public String visitMain(examGrmrParser.MainContext ctx) {
        return visit(ctx.statList());
    }
    
    @Override public String visitStatList(examGrmrParser.StatListContext ctx) {
        String res = "";
        for(examGrmrParser.StatContext sc: ctx.stat())
            res = visit(sc);
        return res;
    }
    
    @Override public String visitStat(examGrmrParser.StatContext ctx) {return visitChildren(ctx);}
    
    @Override public String visitPrint(examGrmrParser.PrintContext ctx) {
        String res = visit(ctx.printV());
        return res;
    }

    @Override public String visitPrintS(examGrmrParser.PrintSContext ctx) {
        String res = visit(ctx.string());
        return res;
    }

    @Override public String visitPrintID(examGrmrParser.PrintIDContext ctx) {
        String key = ctx.ID().getText();
        String res = "";
        if(!incogni.containsKey(key)){
            System.err.println("That variable isnt declared");
            System.exit(0);
        }
        else{
            res = incogni.get(key);
        }
        return res;
    }

    @Override public String visitAssign(examGrmrParser.AssignContext ctx) {
        String key = ctx.ID().getText();
        String res = "";
        if(incogni.containsKey(key)){
            System.err.println("That variable already exists");
            System.exit(0);
        }
        else{
            String val = visit(ctx.assignV());
            incogni.put(key,val);
            res = key + " : " + val;
        }
        return res;
    }

    @Override public String visitAssignIn(examGrmrParser.AssignInContext ctx) {
        String res = visit(ctx.input());
        return res;
    }

    @Override public String visitAssignS(examGrmrParser.AssignSContext ctx) {
        String res = visit(ctx.string());
        return res;
    }

    @Override public String visitAssignOP(examGrmrParser.AssignOPContext ctx) {
        String res = visit(ctx.op());
        return res;
    }

    @Override public String visitOpCon(examGrmrParser.OpConContext ctx) {
        String tmpL = visit(ctx.l);
        String tmpR = visit(ctx.r);
        String res = tmpL + tmpR;
        return res;
    }

    @Override public String visitOpChange(examGrmrParser.OpChangeContext ctx) {
        String tbc = visit(ctx.tbc);
        String stc = visit(ctx.stc);
        String sfc = visit(ctx.sfc);
        String res = tbc.replace(stc,sfc);
        return res;
    }

    @Override public String visitInput(examGrmrParser.InputContext ctx) {
        Scanner scan = new Scanner(System.in);
        System.out.print(visit(ctx.string()));
        String in = scan.nextLine();
        return in;
    }
    
    @Override public String visitString(examGrmrParser.StringContext ctx) {
        String res = ctx.STRING().getText();
        res = res.replace("\"" , "");
        return res;
    }
}