#Miniaturowa tablica informacji pasażersekiej (M.tip)

Aplication about deliever information of time and station in public transport and a lot more... .


Team:

Rafał Rogala,
Wiktor Krzyczkowski,
Dariusz Cichocki,
Radosław Lach,
Witold Weiner,


Team site: https://rrnax.github.io/Miniaturowa_tablica_informacji_pasazerskiej/

How to use Docker compose?

Run this to rebuild your containers:

docker-compose -p m_tip up --build

If u made changes in backend you have to udate jar file first.
The jar file is in /server/target it's SNAPSHOT!!
To do this use comand:

mvn clean package

If you do not have maven in your system you can use InteliJ
On right of your window clik maven ->  api -> lifecycle -> [double click on package]

and that's it.


