void print_array(int *array, size_t size)
{
    for (size_t i = 0;i < size - 1;i++)
    {
        printf("%d | ",*(array + i));
    }
    printf("%d\n",*(array + size - 1));
}

int main(void)
{
    puts("before sorting:");
    int array[9] = { 3, 9, 8, 1, 7, 4, 6, 2, 5 };
    print_array(array, 9);
    heap_sort(array, 9);
    puts("after sorting:");
    print_array(array, 9);
    return 0;
}

