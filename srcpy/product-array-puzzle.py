def printProductArray(array) :
        n = len(array)

        left_product_array = [1] * n
        right_product_array = [1] * n

        i = 1
        j = n-2

        while i < n and j >= 0:
                print(left_product_array)
                print()
                print(right_product_array)
                print()
                left_product_array[i] = left_product_array[i-1] * array[i-1]
                right_product_array[j] = right_product_array[j+1] * array[j+1]
                i = i+1
                j = j-1

        print(left_product_array)
        print()
        print(right_product_array)
        print()

        final_array = [1] * n
        i = 0
        for count in range(n):
                final_array[count] = left_product_array[count]  * right_product_array[count]

        return final_array


print(printProductArray([10, 3, 5, 6, 2]))
