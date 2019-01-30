from http.server import BaseHTTPRequestHandler, HTTPServer, CGIHTTPRequestHandler

def run(server_class=HTTPServer, handler_class=CGIHTTPRequestHandler):
    server_address = ('127.0.0.1', 8000)
    httpd = server_class(server_address, handler_class)
    print("server started", 8000)
    try:
       httpd.serve_forever()
    except:
       pass
    httpd.server_close()
    print("server stopped")

run()
