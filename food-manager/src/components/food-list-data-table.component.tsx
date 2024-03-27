import React from "react";
import {
    Column,
    Table as ReactTable,
    PaginationState,
    useReactTable,
    getCoreRowModel,
    getFilteredRowModel,
    getPaginationRowModel,
    flexRender,
    getSortedRowModel,
} from "@tanstack/react-table"

import { FoodDataTableProps, Food, columns } from "./food-list-columns.component"


const defaultData: Food[] = [
  {
    name: 'testFood1',
    foodType: 'FOOD',
    statusId: 'created',
    createdBy: 'John Doe',
    createdByTimestamp: '02-02-2024 00:30:00.000',
  },
  {
    name: 'testSauce1',
    foodType: 'SAUCE',
    statusId: 'updated',
    createdBy: 'Jane Doe',
    createdByTimestamp: '02-02-2024 00:30:00.000',
  },
  {
    name: 'testSpice1',
    foodType: 'FOOD',
    statusId: 'deleted',
    createdBy: 'John Doe',
    createdByTimestamp: '02-02-2024 00:30:00.000',
  },
]

function Filter({
  column,
  table,
}: {
  column: Column<any, any>
  table: ReactTable<any>
}) {
  const firstValue = table
    .getPreFilteredRowModel()
    .flatRows[0]?.getValue(column.id)

  const columnFilterValue = column.getFilterValue()

  return typeof firstValue === 'number' ? (
    <div className="flex space-x-2">
      <input
        type="number"
        value={(columnFilterValue as [number, number])?.[0] ?? ''}
        onChange={e =>
          column.setFilterValue((old: [number, number]) => [
            e.target.value,
            old?.[1],
          ])
        }
        placeholder={`Min`}
        className="w-24 border shadow rounded"
      />
      <input
        type="number"
        value={(columnFilterValue as [number, number])?.[1] ?? ''}
        onChange={e =>
          column.setFilterValue((old: [number, number]) => [
            old?.[0],
            e.target.value,
          ])
        }
        placeholder={`Max`}
        className="w-24 border shadow rounded"
      />
    </div>
  ) : (
    <input
      type="text"
      value={(columnFilterValue ?? '') as string}
      onChange={e => column.setFilterValue(e.target.value)}
      placeholder={`Search...`}
      className="w-36 border shadow rounded"
    />
  )
}

function FoodTable<TData, TValue>({
    columns,
    data,
  }: FoodDataTableProps<TData, TValue>) {
    const [setData] = React.useState(() => [...defaultData])
    const rerender = React.useReducer(() => ({}), {})[1]

    const [pagination, setPagination] = React.useState<PaginationState>({
        pageIndex: 0,
        pageSize: 10,
    })

    const table = useReactTable({
        data,
        columns,
        debugTable: true,
        getCoreRowModel: getCoreRowModel(),
        getSortedRowModel: getSortedRowModel(),
        getFilteredRowModel: getFilteredRowModel(),
        getPaginationRowModel: getPaginationRowModel(),
        onPaginationChange: setPagination,
        state: {
          pagination,
        },
    })
    return (
        <div>
            <div className="p-2">
                <table>
                  <thead>
                    {table.getHeaderGroups().map(headerGroup => (
                        <tr key={headerGroup.id}>
                          {headerGroup.headers.map(header => {
                            return (
                              <th key={header.id} colSpan={header.colSpan}>
                                <div
                                  {...{
                                    className: header.column.getCanSort()
                                      ? 'cursor-pointer select-none'
                                      : '',
                                    onClick: header.column.getToggleSortingHandler(),
                                  }}
                                >
                                  {flexRender(
                                    header.column.columnDef.header,
                                    header.getContext()
                                  )}
                                  {{
                                    asc: ' 🔼',
                                    desc: ' 🔽',
                                  }[header.column.getIsSorted() as string] ?? null}
                                  {header.column.getCanFilter() ? (
                                    <div>
                                      <Filter column={header.column} table={table} />
                                    </div>
                                  ) : null}
                                </div>
                              </th>
                            )
                          })}
                        </tr>
                    ))}
                  </thead>
                  <tbody>
                    {table.getRowModel().rows.map(row => (
                      <tr key={row.id}>
                        {row.getVisibleCells().map(cell => (
                          <td key={cell.id}>
                            {flexRender(cell.column.columnDef.cell, cell.getContext())}
                          </td>
                        ))}
                      </tr>
                    ))}
                  </tbody>
                  <tfoot>
                    {table.getFooterGroups().map(footerGroup => (
                      <tr key={footerGroup.id}>
                        {footerGroup.headers.map(header => (
                          <th key={header.id}>
                            {header.isPlaceholder
                              ? null
                              : flexRender(
                                  header.column.columnDef.footer,
                                  header.getContext()
                                )}
                          </th>
                        ))}
                      </tr>
                    ))}
                  </tfoot>
                </table>
                <div className="h-4" />
                <div className="flex items-center gap-2">
                    <button
                      className="border rounded p-1"
                      onClick={() => table.firstPage()}
                      disabled={!table.getCanPreviousPage()}
                    >
                      {'<<'}
                    </button>
                    <button
                      className="border rounded p-1"
                      onClick={() => table.previousPage()}
                      disabled={!table.getCanPreviousPage()}
                    >
                      {'<'}
                    </button>
                    <button
                      className="border rounded p-1"
                      onClick={() => table.nextPage()}
                      disabled={!table.getCanNextPage()}
                    >
                      {'>'}
                    </button>
                    <button
                      className="border rounded p-1"
                      onClick={() => table.lastPage()}
                      disabled={!table.getCanNextPage()}
                    >
                      {'>>'}
                    </button>
                    <span className="flex items-center gap-1">
                      <div>Page</div>
                      <strong>
                        {table.getState().pagination.pageIndex + 1} of{' '}
                        {table.getPageCount().toLocaleString()}
                      </strong>
                    </span>
                    <span className="flex items-center gap-1">
                      | Go to page:
                      <input
                        type="number"
                        defaultValue={table.getState().pagination.pageIndex + 1}
                        onChange={e => {
                          const page = e.target.value ? Number(e.target.value) - 1 : 0
                          table.setPageIndex(page)
                        }}
                        className="border p-1 rounded w-16"
                      />
                    </span>
                    <select
                      value={table.getState().pagination.pageSize}
                      onChange={e => {
                        table.setPageSize(Number(e.target.value))
                      }}
                    >
                      {[10, 20, 30, 40, 50].map(pageSize => (
                        <option key={pageSize} value={pageSize}>
                          Show {pageSize}
                        </option>
                      ))}
                    </select>
                  </div>
                  <div>
                    Showing {table.getRowModel().rows.length.toLocaleString()} of{' '}
                    {table.getRowCount().toLocaleString()} Rows
                  </div>
                </div>
        </div>
  )

}

export { FoodTable };