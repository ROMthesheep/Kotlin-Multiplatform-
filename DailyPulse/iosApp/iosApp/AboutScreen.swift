//
//  AboutScreen.swift
//  iosApp
//
//  Created by Luca Lago on 18/7/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct AboutScreen: View {
    var body: some View {
        NavigationStack {
            AboutView()
                .navigationTitle("About Device")
        }
    }
}

#Preview {
    AboutScreen()
}
