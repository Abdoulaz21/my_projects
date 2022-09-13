#include "matrix.hh"

Matrix::Matrix(unsigned rows, unsigned columns)
{
    if (rows == 0)
    {
        std::cerr << "Error: Invalid argument" << rows;
        exit(2);
    }

    if (columns == 0)
    {
        std::cerr << "Error: Invalid argument" << columns;
        exit(2);
    }

    rows_ = rows;
    columns_ = columns;
    int **lines = new int *[rows];
    for (unsigned i = 0; i < rows; i++)
    {
        lines[i] = new int[columns];
    }
    my_mat_ = lines;
}

Matrix::~Matrix()
{
    for (unsigned i = rows_; i > 1; i--)
        delete[] my_mat_[i - 1];
    delete[] my_mat_;
}

void Matrix::fill(int val)
{
    unsigned row = rows_;
    unsigned col = columns_;
    for (unsigned i = 0; i < row; i++)
    {
        for (unsigned j = 0; j < col; j++)
        {
            my_mat_[i][j] = val;
        }
    }
}

void Matrix::print() const
{
    unsigned row = rows_;
    unsigned col = columns_;
    for (unsigned i = 0; i < row; i++)
    {
        for (unsigned j = 0; j < col; j++)
        {
            std::cout << my_mat_[i][j];
            if (j < col - 1)
                std::cout << ' ';
        }
        std::cout << '\n';
    }
}

bool Matrix::is_square() const
{
    if (Matrix::rows_ == columns_)
        return true;
    return false;
}

int Matrix::trace() const
{
    if (rows_ == columns_)
    {
        int res = 0;
        for (unsigned i = 0; i < rows_; i++)
            res += my_mat_[i][i];
        return res;
    }
    std::cerr << "Error: invalid matrix\n";
    exit(3);
}

Matrix Matrix::transpose() const
{
    unsigned row = rows_;
    unsigned col = columns_;
    Matrix tr_mat(col, row);
    for (unsigned i = 0; i < row; i++)
    {
        for (unsigned j = 0; j < col; j++)
        {
            tr_mat.value_set(j, i, my_mat_[i][j]);
        }
    }
    return tr_mat;
}

void Matrix::value_set(unsigned row, unsigned column, int val)
{
    if (row >= rows_)
    {
        std::cerr << "Error: invalid argument " << row << '\n';
        exit(2);
    }

    if (column >= columns_)
    {
        std::cerr << "Error: invalid argument " << column << '\n';
        exit(2);
    }

    my_mat_[row][column] = val;
}

int Matrix::value_get(unsigned row, unsigned column) const
{
    if (row >= rows_)
    {
        std::cerr << "Error: invalid argument " << row << '\n';
        exit(2);
    }

    if (column >= columns_)
    {
        std::cerr << "Error: invalid argument " << column << '\n';
        exit(2);
    }

    return my_mat_[row][column];
}

unsigned Matrix::rows_get() const
{
    return rows_;
}

unsigned Matrix::columns_get() const
{
    return columns_;
}
