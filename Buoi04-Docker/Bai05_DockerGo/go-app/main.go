package main

import (
	"fmt"
	"net/http"
)

func handler(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "Xin chào từ ứng dụng Go trong Docker!")
}

func main() {
	http.HandleFunc("/", handler)
	fmt.Println("Server đang chạy trên cổng 8080...")
	http.ListenAndServe(":8080", nil)
}