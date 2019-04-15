import java.util.*;

public class sjfnp{

    Scanner sc; 
    int n_process;
    int process[], at[], bt[];
    int tat[], wt[], et[], process_sequence[];
    Boolean visited[];
    int time;
    int process_cnt;
    float avg_tat, avg_wt;

    sjfnp(){
        sc = new Scanner(System.in);
        time = 0;
        process_cnt = 0;
        avg_tat = 0.0f;
        avg_wt = 0.0f;
    }

    void input(){
        System.out.print("No of Process : ");
        n_process = sc.nextInt();

        process = new int[n_process];
        at = new int[n_process];
        bt = new int[n_process];
        tat = new int[n_process];
        wt = new int[n_process];
        et = new int[n_process];
        process_sequence = new int[n_process];
        visited = new Boolean[n_process];

        System.out.print("Enter Process IDs : ");
        for(int i = 0; i < n_process; i++){
            process[i] = sc.nextInt();
        }

        System.out.print("Enter Arrival Time : ");
        for(int i = 0; i < n_process; i++){
            at[i] = sc.nextInt();
        }

        System.out.print("Enter Burst Time : ");
        for(int i = 0; i < n_process; i++){
            bt[i] = sc.nextInt();
        }
    }

    int get_min(){
        int pid = 0;
        int min_bt = Integer.MAX_VALUE;
        for(int i = 0; i < n_process; i++){
            if(at[i] <= time && !visited[i]){
                if(min_bt > bt[i]){
                    min_bt = bt[i];
                    pid = i;
                }
            }
        }
        return pid;
    }

    void findAvgTime(){
        //find minimum arrival time
        time = Integer.MAX_VALUE;

        for(int i = 0; i < n_process; i++){
            if(at[i] < time){
                time = at[i];
            }
            visited[i] = false;
        }

        while(process_cnt < n_process){
            //get pid which has min BT in time
            int pid = get_min();

            if(time >= at[pid]){ //when time has gone ahead but AT of pid is less
                time += bt[pid];
                et[pid] += time;
                visited[pid] = true;
                process_sequence[process_cnt] = pid;
                process_cnt++;
            }else{              // ideal case process is not there
                while(time < at[pid]){
                    time++;
                }
                time += bt[pid];
                et[pid] += time;
                visited[pid] = true;
                process_sequence[process_cnt] = pid;
                process_cnt++;
            }
        }

        for(int i = 0; i < n_process; i++){
            tat[i] = et[i] - at[i];
            wt[i] = tat[i] - bt[i];
            avg_wt += wt[i];
            avg_tat += tat[i];
        }

        avg_wt = avg_wt / n_process;
        avg_tat = avg_tat / n_process;

    }

    void output(){
        System.out.println("==================OUTPUT==================");
        System.out.println("PID\tAT\tBT\tCT\tTAT\tWT");
        for(int i = 0; i < n_process; i++){
            System.out.println(process[i]+"\t"+at[i]+"\t"+bt[i]+"\t"+et[i]+"\t"+tat[i]+"\t"+wt[i]);
        }

        System.out.println("\nAverage WT : " + avg_wt);
        System.out.println("Average TAT : " + avg_tat);

        System.out.print("Process Sequence : ");
        for(int i = 0; i < n_process - 1; i++){
            System.out.print(process_sequence[i]+" -> ");
        }
        System.out.println(process_sequence[n_process - 1]);
    }

    public static void main(String args[]){
        sjfnp obj = new sjfnp();
        obj.input();
        obj.findAvgTime();
        obj.output();
    }
}

/*

5
1 2 3 4 5
0 1 2 3 4
8 1 3 2 6

*/