//
//  ContentView.swift
//  iosSim
//
//  Created by Liliia on 14/11/2019.
//  Copyright © 2019 LA. All rights reserved.
//

import SwiftUI
import sample_mpp_app

struct ContentView: View {
    
    var body: some View {
        Text(GetDeviceModelKt.getFullDeviceInfo())
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
