import java.util.*;

public class sjfp{

    Scanner sc;
    int n_process;
    int process[], at[], bt[], bt0[];
    int et[], wt[], tat[];
    int process_sequence[];
    int process_cnt, process_cnt_finish;
    int time;
    float avg_wt, avg_tat;

    sjfp(){
        sc = new Scanner(System.in);
        time = 0;
        avg_wt = 0.0f;
        avg_tat = 0.0f;
        n_process = 0;
        process_cnt = 0;
        process_cnt_finish = 0;
    }

    void input(){
        System.out.print("No of Process :");
        n_process = sc.nextInt();

        process = new int[n_process];
        at = new int[n_process];
        bt = new int[n_process];
        bt0 = new int[n_process];
        et = new int[n_process];
        wt = new int[n_process];
        tat = new int[n_process];
        process_sequence = new int[1000];

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

    int get_min_bt(){
        int pid = -1;
        int min_bt = Integer.MAX_VALUE;
        for(int i = 0; i < n_process; i++){
            if(at[i] <= time && bt[i] > 0){
                if(bt[i] < min_bt){
                    min_bt = bt[i];
                    pid = i;
                }
            }
        }
        return pid;
    }

    void findAvgTime(){
        
        //get minimum AT
        time = Integer.MAX_VALUE;

        for(int i = 0; i < n_process; i++){
            if(at[i] < time){
                time = at[i];
            }
        }

        while(process_cnt_finish < n_process){
            int pid = get_min_bt();
            time++;
            bt[pid] -= 1;
            process_sequence[process_cnt] = pid;
            process_cnt++;
            et[pid] = time; 
            if(bt[pid] == 0){
                process_cnt_finish++;
            }
        }

        for(int i = 0; i < n_process; i++){
            tat[i] = et[i] - at[i];
            wt[i] = tat[i] - bt0[i];
            avg_wt += wt[i];
            avg_tat += tat[i];
        }

        avg_wt = avg_wt / n_process;
        avg_tat = avg_tat / n_process;
    }

    void output(){
        System.out.println("===================OUTPUT===================");
        System.out.println("PID\tAT\tBT\tCT\tTAT\tWT");
        for(int i = 0; i < n_process; i++){
            System.out.println(process[i] + "\t" + at[i] + "\t" + bt0[i] + "\t" + et[i] + "\t" + tat[i] + "\t" + wt[i]);
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
        sjfp obj = new sjfp();
        obj.input();
        obj.findAvgTime();
        obj.output();
    }
}