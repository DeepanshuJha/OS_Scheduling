import java.util.*;

public class priority{

    Scanner sc;
    int n_process;
    int process[], at[], bt[], pty[];
    int et[], wt[], tat[];
    int process_sequence[];
    int process_cnt;
    Boolean visited[];
    int time;
    float avg_wt, avg_tat;

    priority(){
        sc = new Scanner(System.in);
        time = 0;
        avg_wt = 0.0f;
        avg_tat = 0.0f;
        process_cnt = 0;
    }

    void input(){
        System.out.print("No of Process :");
        n_process = sc.nextInt();

        process = new int[n_process];
        at = new int[n_process];
        bt = new int[n_process];
        pty = new int[n_process];
        et = new int[n_process];
        wt = new int[n_process];
        tat = new int[n_process];
        process_sequence = new int[n_process];
        visited = new Boolean[n_process];

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

        System.out.print("Enter Priority (Highest Number is high Priority) : ");
        for(int i = 0; i < n_process; i++){
            pty[i] = sc.nextInt();
        }
    }

    int get_high_priority(){
        int pid = -1;
        int max_pri = Integer.MIN_VALUE;
        for(int i = 0; i < n_process; i++){
            if(at[i] <= time && !visited[i]){
                if(pty[i] > max_pri){
                    max_pri = pty[i];
                    pid = i;
                }
            }
        }
        return pid;
    }

    void findAvgTime(){
        
        time = Integer.MAX_VALUE;
        for(int i = 0; i < n_process; i++){
            if(at[i] < time){
                time = at[i];
            }
            visited[i] = false;
        }

        while(process_cnt < n_process){
            int pid = get_high_priority();

            if(time >= at[pid]){
                time += bt[pid];
                et[pid] += time;
                visited[pid] = true;
                process_sequence[process_cnt] = pid;
                process_cnt++;
            }else{
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
            avg_tat += tat[i];
            avg_wt += wt[i];
        }

        avg_wt = avg_wt / n_process;
        avg_tat = avg_tat / n_process;
    }

    void output(){
        System.out.println("==================OUTPUT==================");
        System.out.println("PID\tAT\tBT\tPRTY\tCT\tTAT\tWT");
        for(int i = 0; i < n_process; i++){
            System.out.println(process[i]+"\t"+at[i]+"\t"+bt[i]+"\t"+pty[i]+"\t"+et[i]+"\t"+tat[i]+"\t"+wt[i]);
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
        priority obj = new priority();
        obj.input();
        obj.findAvgTime();
        obj.output();
    }
}

/*

6
0 1 2 3 4 5
0 1 2 3 4 5
4 5 1 2 3 6
4 5 7 2 1 6

*/