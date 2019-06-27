import random
import math
import sys 


class Bug:
	def __init__(self,x,y,type):
		self.x = x
		self.y = y
		self.type = type


bugs = []

originX = 512
originY = 384


type = sys.argv[1]
radius = float(sys.argv[2])
amount = int(sys.argv[3])

for i in range(0,amount):
	angle = random.random()*math.pi*2
	x = originX + radius*math.cos(angle)
	y = originY + radius*math.sin(angle)
	bugs.append(Bug(x,y,type))

for bug in bugs[:-1]:
	print('{\"xPos\": '+str(bug.x) + ',')
	print('\"yPos\": '+str(bug.y) + ',')
	print('\"type\": "'+bug.type + '"\n},')

print('{\"xPos\": '+str(bugs[-1].x) + ',')
print('\"yPos\": '+str(bugs[-1].y) + ',')
print('\"type\": "'+bugs[-1].type + '"\n}')
