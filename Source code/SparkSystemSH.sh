# clear local folder location & download spark ml project jar
rm -r artificial*
rm -r emotion*
wget http://$1:$2/download/artificial.jar
wget http://$1:$2/download/emotion.csv

# here execute Spark MLlib project jar with data file and client details
spark-submit --class cloudduggu.com.Artificial artificial.jar emotion.csv $1 $2

