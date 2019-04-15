import java.util.*;

public class round_robin{

    Scanner sc;
    Queue<Integer> q;
    HashMap<Integer, Integer> h = new HashMap<>();
    int n_process, quantum;
    int process[], at[], bt[], bt0[];
    int et[], tat[], wt[];
    int process_sequence[];
    int process_cnt;
    int time;
    float avg_wt;
    float avg_tat;

    round_robin(){
        sc = new Scanner(System.in);
        n_process = 0;
        quantum = 0;
        process_cnt = 0;
        avg_wt = 0.0f;
        avg_tat = 0.0f;
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
        bt0 = new int[n_process];
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
            bt0[i] = bt[i];
        }
    }

    void findAvgTime(){
        time = Integer.MAX_VALUE;
        int start_pid = -1;
        for(int i = 0; i < n_process; i++){
            if(at[i] < time){
                time = at[i];
                start_pid = i;
            }
            h.put(i, 0);
        }
        System.out.println("Start Time : " + time);
        q.add(start_pid); //start process id
        h.put(start_pid, 1);
        while(q.size() != 0){
            System.out.println("Queue : " + q);
            int pid = q.remove();
            h.put(pid, 0);
            if(bt[pid] < quantum){
                time += bt[pid];
                et[pid] = time;
                bt[pid] -= bt[pid];
                process_sequence[process_cnt] = pid;
                process_cnt++;
            }else{
                time += quantum;
                et[pid] = time;
                bt[pid] -= quantum; 
                process_sequence[process_cnt] = pid;
                process_cnt++;
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

        for(int i = 0; i < n_process; i++){
            tat[i] = et[i] - at[i];
            wt[i] = tat[i] - bt0[i];
            avg_wt += wt[i];
            avg_tat += tat[i];
        }

        avg_tat /= n_process;
        avg_wt /= n_process;
    }

    void output(){
        System.out.println("==================OUTPUT==================");
        System.out.println("PID\tAT\tBT\tCT\tTAT\tWT");
        for(int i = 0; i < n_process; i++){
            System.out.println(process[i]+"\t"+at[i]+"\t"+bt0[i]+"\t"+et[i]+"\t"+tat[i]+"\t"+wt[i]);
        }

        System.out.println("\nAverage WT : " + avg_wt);
        System.out.println("Average TAT : " + avg_tat);

        System.out.print("Process Sequence : ");
        for(int i = 0; i < process_cnt - 1; i++){
            System.out.print(process_sequence[i]+" -> ");
        }
        System.out.println(process_sequence[process_cnt - 1]);
    }   

    public static void main(String args[]){
        round_robin obj = new round_robin();
        obj.input();
        obj.findAvgTime();
        obj.output();
    }
}

/*

n_process = 4
quantum = 2
pids = 0 1 2 3
at[] = 0 1 2 4
bt[] = 5 4 2 1


*/