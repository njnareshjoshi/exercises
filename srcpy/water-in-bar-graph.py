def countWater(array) :

	n = len(array)

	max_left_list = [-1] * n
	max_right_list = [-1] * n

	print(max_left_list)
	print(max_right_list)
	print()

	max_left = array[0]
	max_right = array[-1]

	i = 1
	j = n-2

	while i < n or j > 0 :

		max_left = max(array[i-1], max_left)
		max_left_list[i] = max_left;
		i = i + 1

		max_right = max(array[j+1], max_right)
		max_right_list[j] = max_right
		j = j - 1
		
		print(max_left_list)
		print(max_right_list)
		print()

	total_quantity = 0
	for count in range(1, n-1) :
		quantity = min(max_left_list[count], max_right_list[count]) - array[count];
		total_quantity += quantity
		print(f'{count} {max_left_list[count]} {max_right_list[count]} {quantity}')

	return total_quantity


print(countWater([5, 1, 3, 4]))