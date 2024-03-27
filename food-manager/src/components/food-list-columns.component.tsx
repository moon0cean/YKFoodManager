import * as React from 'react'
import ReactDOM from 'react-dom/client'
import { ColumnDef } from "@tanstack/react-table"

export type Food = {
  name: string
  foodType: string
  statusId: "created" | "updated" | "deleted"
  createdBy: string
  createdByTimestamp: string
}

export interface FoodDataTableProps<TData, TValue> {
  columns: ColumnDef<TData, TValue>[]
  data: TData[]
}

export const columns: ColumnDef<Food>[] = [
  {
    accessorKey: "name",
    header: "Food name",
  },
  {
    accessorKey: "foodType",
    header: "Food type",
  },
  {
    accessorKey: "statusId",
    header: "Status",
  },
  {
    accessorKey: "createdBy",
    header: "Created by",
  },
  {
    accessorKey: "createdByTimestamp",
    header: "Created date",
  },
]
