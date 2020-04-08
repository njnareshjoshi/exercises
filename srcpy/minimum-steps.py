'''
Program to calculate maximum steps require to make a number 1 from n while the steps we can take is
subtract by 1, divide by 2 if divisible, divide by 3 if divisible
'''

def get_min_steps_using_recursion(n):

	print('Solving minimum steps problem using recursion')

	if n == 1 :
		return 0

	print(f'number is {n} subtracting 1 now number becomes {n-1}')
	count = get_min_steps_using_recursion(n-1)
	
	if n % 2 == 0 :
		print(f'number is {n} dividing by 2 now number becomes {int(n/2)}')
		count = min(count, get_min_steps_using_recursion(int(n/2)))

	if n % 3 == 0 :
		print(f'number is {n} dividing by 3 now number becomes {int(n/3)}')
		count = min(count, get_min_steps_using_recursion(int(n/3)))

	return 1 + count


def get_min_steps_using_memoization(n, memo = None):

	print('Solving minimum steps problem using memoization')

	if(memo is None) :
		memo = [-1] * (n+1)
		memo[1] = 0

	if memo[n] != -1 :
		return memo[n];

	print(f'number is {n} subtracting 1 now number becomes {n-1}')
	count = get_min_steps_using_memoization(n-1, memo)
	
	if n % 2 == 0 :
		print(f'number is {n} dividing by 2 now number becomes {int(n/2)}')
		count = min(count, get_min_steps_using_memoization(int(n/2), memo))

	if n % 3 == 0 :
		print(f'number is {n} dividing by 3 now number becomes {int(n/3)}')
		count = min(count, get_min_steps_using_memoization(int(n/3), memo))

	memo[n] = 1 + count
	print(f'changing memo to {memo}')

	return memo[n]


def get_min_steps_using_dp(n, dp = None):

	print('Solving minimum steps problem using tabulation')

	if(dp is None) :
		dp = [0] * (n+1)
		dp[1] = 0

	for i in range (2, n+1):
		
		print(f'number is {i} subtracting 1 now number becomes {i-1}')
		count = dp[i-1]
		
		if i % 2 == 0 :
			print(f'number is {i} dividing by 2 now number becomes {int(i/2)}')
			count = min(count, dp[int(i/2)])

		if i % 3 == 0 :
			print(f'number is {i} dividing by 3 now number becomes {int(i/3)}')
			count = min(count, dp[int(i/3)])

		dp[i] = 1 + count
		print(f'changing dp to {dp}')

	return dp[n]


print(get_min_steps_using_recursion(10))
print(get_min_steps_using_memoization(10))
print(get_min_steps_using_dp(10))