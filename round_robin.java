import java.awt.SystemTray;
import java.util.*;

public class round_robin{

    Scanner sc;
    Queue<Integer> q;
    HashMap<Integer, Integer> h = new HashMap<>();
    int n_process, quantum;
    int process[], at[], bt[];
    int et[], tat[], wt[];
    int process_sequence[];
    int process_cnt;
    int time;

    round_robin(){
        sc = new Scanner(System.in);
        n_process = 0;
        quantum = 0;
        process_cnt = 0;
        q = new LinkedList<>();
    }

    void input(){

        System.out.print("No of process : ");
        n_process = sc.nextInt();

        System.out.print("Time quantum : ");
        quantum = sc.nextInt();

        process = new int[n_process];
        at = new int[n_process];
        bt = new int[n_process];
        et = new int[n_process];
        tat = new int[n_process];
        wt = new int[n_process];
        process_sequence = new int[100];

        System.out.print("Enter PIDs :");
        for(int i = 0; i < n_process; i++){
            process[i] = sc.nextInt();
        }

        System.out.print("Enter AT : ");
        for(int i = 0; i < n_process; i++){
            at[i] = sc.nextInt();
        }

        System.out.print("Enter BT : ");
        for(int i = 0; i < n_process; i++){
            bt[i] = sc.nextInt();
        }
    }

    void findAvgTime(){
        time = Integer.MAX_VALUE;
        for(int i = 0; i < n_process; i++){
            if(at[i] < time){
                time = at[i];
            }
            h.put(i, 0);
        }
        System.out.println("Start Time : " + time);
        q.add(0); //start process id
        h.put(0, 1);
        while(q.size() != 0){
            System.out.println("Queue : " + q);
            int pid = q.remove();
            h.put(pid, 0);
            if(bt[pid] < quantum){
                time += bt[pid];
                et[pid] = time;
                bt[pid] -= bt[pid];
            }else{
                time += quantum;
                et[pid] = time;
                bt[pid] -= quantum; 
            }
            for(int i = 0; i < n_process; i++){
                if(h.containsKey(i)){
                    int check = h.get(i);
                    if(i != pid && check != 1){
                        if(at[i] <= time && bt[i] > 0){
                            q.add(i);
                            h.put(i, 1);
                        }
                    }
                }
            }
            if(bt[pid] > 0){
                q.add(pid);
                h.put(pid, 1);
            }
        }
    }

    void output(){
        System.out.println("==================OUTPUT==================");
        System.out.println("PID\tAT\tBT\tCT\tTAT\tWT");
        for(int i = 0; i < n_process; i++){
            System.out.println(process[i]+"\t"+at[i]+"\t"+bt[i]+"\t"+et[i]);
        }
    }   

    public static void main(String args[]){
        round_robin obj = new round_robin();
        obj.input();
        obj.findAvgTime();
        obj.output();
    }
}

/*

4
2
0 1 2 3
0 1 2 4
5 4 2 1


*/