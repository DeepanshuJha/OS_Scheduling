import java.util.*;

public class fcfs{

    static void sort(int process[], int n_process, int burst_time[], int arrival_time[]){
        for(int i = 0; i < n_process; i++){
            for(int j = 0; j + 1 < n_process; j++){
                if(arrival_time[j] > arrival_time[j + 1]){
                    int temp = arrival_time[j];
                    arrival_time[j] = arrival_time[j + 1];
                    arrival_time[j + 1] = temp;

                    temp = burst_time[j];
                    burst_time[j] = burst_time[j + 1];
                    burst_time[j + 1] = temp;

                    temp = process[j];
                    process[j] = process[j + 1];
                    process[j + 1] = temp;
                }
            }
        }
    }

    static void output(int process[], int n_process, int burst_time[], int arrival_time[], int wait_time[], int execution_time[], int tat[], float avg_tat, float avg_wt){
        System.out.println("\n===============OUTPUT=================");
        System.out.println("PID\tAT\tBT\tET\tTAT\tWT");
        for(int i = 0; i < n_process; i++){
            System.out.println(process[i]+"\t"+arrival_time[i]+"\t"+burst_time[i]+"\t"+execution_time[i]+"\t"+tat[i]+"\t"+wait_time[i]);
        }
    
        System.out.println("\nAverage TAT : " + avg_tat);
        System.out.println("Average WT : " + avg_wt);
    }

    static void findAvgTime(int process[], int n_process, int burst_time[], int arrival_time[]){
        sort(process, n_process, burst_time, arrival_time);

        int time = 0; // Gantt Chart time
        int wait_time[] = new int[n_process];
        int execution_time[] = new int[n_process];
        int tat[] = new int[n_process];

        for(int i = 0; i < n_process; i++){
            if(time >= arrival_time[i]){
                time += burst_time[i];
                execution_time[i] += time;
            }
            else{
                while(time < arrival_time[i]){
                    time++;
                }
                time += burst_time[i];
                execution_time[i] += time;
            }
        }

        float avg_wt = 0.0f;
        float avg_tat = 0.0f;

        for(int i = 0; i < n_process; i++){
            tat[i] = execution_time[i] - arrival_time[i];
            wait_time[i] = tat[i] - burst_time[i];
            avg_wt += wait_time[i];
            avg_tat += tat[i];
        }

        avg_wt = avg_wt / n_process;
        avg_tat = avg_tat / n_process;

        output(process, n_process, burst_time, arrival_time, wait_time, execution_time, tat, avg_tat, avg_wt);
    }
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);

        int n_process = 0;
        System.out.print("No of Processes : ");
        n_process = sc.nextInt();

        int process[] = new int[n_process];

        System.out.print("Enter Process Ids : ");
        for(int i = 0; i < n_process; i++){
            process[i] = sc.nextInt();
        }

        int burst_time[] = new int[n_process];

        System.out.print("Enter Burst Time : ");
        for(int i = 0; i < n_process; i++){
            burst_time[i] = sc.nextInt();
        }

        int arrival_time[] = new int[n_process];

        System.out.print("Enter Arrival Time : ");
        for(int i = 0; i < n_process; i++){
            arrival_time[i] = sc.nextInt();
        }

        findAvgTime(process, n_process, burst_time, arrival_time);

    }
}