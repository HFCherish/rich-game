307 ：
玩家在end turn状态，不能执行命令  －10 : 13
玩家在wait for command状态，可以执行命令 － 10
玩家在wait for response状态，可以回复命令 － 10
玩家在wait for command状态，
执行终极(roll)不需要回复的命令之后，wait for turn －5
执行终极(roll)需要回复(buy estate, upgrade estate, buy tool, select gift)的命令之后，wait for response －3
执行非终极命令(非roll）之后，wait for command －3
执行命令（pass other estate）之后funds小于0，bankrupt －3
玩家在wait for response状态，
执行可以循环response（buy tool）的命令，wait for response －2
执行可以不可以循环response（非buy tool）的命令，wait for turn －2
执行可以循环response（buy tool）的命令，并且退出后，wait for turn －2
游戏开始后，默认第一个player是当前玩家 －10 : 7
游戏开始（game_start）后，可以结束游戏
如果手动结束游戏，game_end －5 : 4
如果只有一个player没有bankrupt，game_end －10 : 10
游戏开始后，可以接受玩家输入
如果当前玩家在wait for command状态，
可以指定当前玩家执行命令 － 5 : 7
执行命令后，上一个command是刚刚执行的command － 5 ：3
不可以回复命令 －2 : 3
游戏开始后，如果当前玩家在wait for response状态，
可以指定当前玩家回复命令－3 : 4
不可以执行命令－1: 2
游戏开始后，可以切换玩家
可以切换到下一玩家 －5 : 3
如果下一玩家bankrupt后，切换到下一玩家 －5 : 7
如果下一玩家停留在医院或监狱，切换到下一玩家 －5 : 2
玩家输入roll命令
玩家走到empty estate，
如果钱够，wait for response －5 : 15
如果response yes，买房，end turn －5
response no，不买，end turn －1
如果钱不够，end turn －1
走到own estate
如果钱够，wait for response －5
response yes， upgrade， end turn －5
response no，不upgrade，end turn －1
钱不够，end turn －1
走到others estate
钱够，交过路费，end turn －5
钱不够，交过路费，bankrupt －5
如果有福神，end turn －5
如果房主在监狱或医院，end turn －5
走到toolhouse
如果点数够，可以一直wait for response选择工具 －5
选择工具后，买工具，如果点数够，wait for response 选择工具 －5
F后，end turn －5
点数不够，end turn －1
走到gifthouse
wait for response
输入正确，获得gift，end turn －10
资金卡：加钱，end turn
point卡：加点数，end turn
福神：获得福神，end turn
福神5轮内有效 －10
输入错误，不获得gift，end turn －3
走到prison，进入prison，end turn －5
在prison待两天 －10
走到hospital，end turn －3
走到magic house，end turn －3
经过炸弹，送进医院，end turn －3
医院待三天 －3
经过block，停止，进到block后的place －3
走到mineral estate，加点数，end turn －5
block n，
如果没有block，wait for command －5
如果有block，在地图上放block，wait for command －5
bomb n
如果没有bomb，wait for command －5
如果有bomb，在地图上放bomb，wait for command －5
robot
如果没有robot，wait for command －3
如果有robot，在地图上使用robot，wait for command －3
sell x
如果有房产，卖房产，置为空地，wait for command －5
如果没有，wait for command －3
sellTool x
如果有tool，卖tool，wait for command －5
如果没有，wait for command －5
query，打印player信息，wait for command －10
help，打印help信息，wait for command －5
可以从map的一个点移动到另一个点
正向移动  －5
逆向移动 －5
经过炸弹或block就停 －10
可以在map上放block，bomb
能在指定地点放 －5
只能在前后十步放 －5
如果有人不能放 －5
如果有道具不能放 －5
可以在map上使用robot清扫前后10步的道具 －10