#Morgan Stanley
#Given input "aaabbca", output "a3b2c1a1": Count consecutive characters
#O(n)
strg = "aaabbca"

count = 1
if len(strg) > 1:
	for i in range(1, len(strg)):
		if strg[i-1] == strg[i]:
			count +=1
		else:
			print(strg[i-1] + str(count), end='', flush=True)
			count = 1
	print(strg[i] + str(count)) 